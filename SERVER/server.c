#include <stdio.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <sys/un.h>
#include <unistd.h>
#include <netinet/in.h>
#include <pthread.h>

#define NUMBERS_CONNECTION_CLIENTS_IN_WAIT 10
#define N N

void *connection_handler(void *a);
int checkWinner(int matrix[][3]);
void insertMatrix(int matrix[][3], int posButton, int a);
int readWrite(int user1, int user2, int matrix[][3], int a);
void notifyVictory(int winner, int loser);
void initializeMatrix(int matrix[][3]);
void victoryForDisconnection(int client);
void notifyTie(int user1, int user2);
void printLog(char* str);
void printError(char* str);



struct Game{
    int user1;
    int user2;
};
typedef struct Game game;


pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;


int main() {

    int server_sd, client_sd, client_sd2;
    int *thread_sd;
    int *thread_sd2;
    game *partita;
    struct sockaddr_in server_addr;
    struct sockaddr_in client_addr;
    struct sockaddr_in client_addr2;
    socklen_t client_len = sizeof(client_addr);
    socklen_t client_len2 = sizeof(client_addr2);

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(N);
    server_addr.sin_addr.s_addr  = htonl(INADDR_ANY);

    server_sd = socket(PF_INET, SOCK_STREAM, 0);
    if(server_sd==-1){
        printError("[SOCKET] Errore creazioen socket\n");
        exit(EXIT_FAILURE);
    }

    int sockopt = setsockopt(server_sd, SOL_SOCKET, SO_REUSEADDR, &(int){1}, sizeof(int));
    if(sockopt==-1){
        printError("[SOCKET] Errore riutilizzo socket\n");
        exit(EXIT_FAILURE);
    }

    if(bind(server_sd, (struct sockaddr *) &server_addr, sizeof(server_addr))==-1){
        printError("[SOCKET] Errore bind socket\n");
        exit(EXIT_FAILURE);
    }

    if(listen(server_sd, NUMBERS_CONNECTION_CLIENTS_IN_WAIT)==-1){
        printError("[SOCKET] Errore listen socket\n");
        exit(EXIT_FAILURE);
    }


    while(1){

        client_sd = accept(server_sd, (struct sockaddr*)&client_addr, &client_len);
        if(client_sd==-1){
            printError("[CONNESSIONE] Errore accept\n");
            exit(EXIT_FAILURE);
        }
        printLog("[CONNESSIONE] Client connesso\n");

        client_sd2 = accept(server_sd, (struct sockaddr*)&client_addr2, &client_len2);
        if(client_sd2==-1){
            printError("[CONNESSIONE] Errore accept\n");
            exit(EXIT_FAILURE);
        }
        printLog("[CONNESSIONE] Client connesso\n");



        pthread_t tid;
        thread_sd = (int*)malloc(sizeof(int));
        *thread_sd = client_sd;

        thread_sd2 = (int*)malloc(sizeof(int));
        *thread_sd2 = client_sd2;


        partita=(game*)malloc(sizeof(game));
        partita->user1=*thread_sd;
        partita->user2=*thread_sd2;

        if(pthread_create(&tid,NULL,connection_handler,(void*)partita)!=0){
            printError("[THREAD] Errore creazione thread partita\n");
            exit(EXIT_FAILURE);
        }
        printLog("[THREAD] Thread partita creato\n");


    }

    close(server_sd);

    return 0;
}

void printError(char* str){
    int len;
    len=strlen(str);
    pthread_mutex_lock(&mutex);
    write(STDERR_FILENO, str, len);
    pthread_mutex_unlock(&mutex);
}


void printLog(char* str){
    int len;
    len=strlen(str);
    pthread_mutex_lock(&mutex);
    write(STDOUT_FILENO, str, len);
    pthread_mutex_unlock(&mutex);
}


void *connection_handler(void *a){

    game thread_sd = *(game*)a;
    int round = 0, win = 0;
    int matrix[3][3];
    initializeMatrix(matrix);
    ssize_t write_size;

    write_size = write(thread_sd.user1, "X\n", 2);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }

    write_size = write(thread_sd.user2, "O\n", 2);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }

    while(1){

        win = readWrite(thread_sd.user1, thread_sd.user2,matrix,1);
        if(win == 1){

            notifyVictory(thread_sd.user1, thread_sd.user2);
            break;

        }else if(win == -1) {

            victoryForDisconnection(thread_sd.user2);
            break;

        }

        if(round < 4){

            win = readWrite(thread_sd.user2, thread_sd.user1,matrix,2);
            round++;

            if(win == 2){

                notifyVictory(thread_sd.user2, thread_sd.user1);
                break;

            }else if(win == -1) {

                victoryForDisconnection(thread_sd.user1);
                break;

            }
        } else {

            notifyTie(thread_sd.user1, thread_sd.user2);
            break;

        }

    }

    close(thread_sd.user1);
    close(thread_sd.user2);
    free(a);
    pthread_detach(pthread_self());
    pthread_exit(NULL);

}

void victoryForDisconnection(int client){

    ssize_t write_size = write(client, "vfd\n", 4);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }

}

void notifyTie(int user1, int user2){

    ssize_t write_size;
    write_size = write(user1, "pareggio\n", 10);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
        exit(EXIT_FAILURE);
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }

    write_size = write(user2, "pareggio\n", 10);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
        exit(EXIT_FAILURE);
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }
}


void initializeMatrix(int matrix[][3]){
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            matrix[i][j] = 0;
        }
    }
}


void notifyVictory(int winner, int loser){

    ssize_t write_size;

    write_size = write(winner, "vittoria\n", 10);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
        exit(EXIT_FAILURE);
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }

    write_size = write(loser, "sconfitta\n", 11);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
        exit(EXIT_FAILURE);
    } else {
        printLog("[WRITE] Write effettuata correttamente\n");
    }
}



int readWrite(int user1, int user2, int matrix[][3], int a){

    char client_message[10];
    bzero(client_message, 10);

    ssize_t read_size = read(user1, client_message, 1);
    if(read_size == -1){
        printError("[READ] Errore lettura\n");
        exit(EXIT_FAILURE);
    }else if(read_size == 0){
        return -1;
    }
    printLog("[READ] Read effettuata correttamente\n");

    int posButton = atoi(client_message);

    insertMatrix(matrix, posButton, a);

    int checkWin = checkWinner(matrix);

    ssize_t write_size = write(user2, strcat(client_message, "\n"), 2);
    if(write_size==-1){
        printError("[WRITE] Errore scrittura\n");
        exit(EXIT_FAILURE);
    }
    printLog("[WRITE] Write effettuata correttamente\n");

    if(checkWin != 0){
        return checkWin;
    }

    return 0;

}

int checkWinner(int matrix[][3]){
    int i = 0;

    for(i=0; i<3; i++)  /* check rows */
        if(matrix[i][0]==matrix[i][1] && matrix[i][0]==matrix[i][2]){

            if(matrix[i][0]==1){
                return matrix[i][0];
            } else if(matrix[i][0]==2){
                return matrix[i][0];
            }

        }


    for(i=0; i<3; i++)  /* check columns */
        if(matrix[0][i]==matrix[1][i] && matrix[0][i]==matrix[2][i]){

            if(matrix[0][i]==1){
                return matrix[0][i];
            } else if(matrix[0][i]==2){
                return matrix[0][i];
            }
        }


    /* test diagonals */
    if(matrix[0][0]==matrix[1][1] && matrix[1][1]==matrix[2][2]){

        if(matrix[0][0]==1){
            return matrix[0][0];
        } else if(matrix[0][0]==2){
            return matrix[0][0];
        }
    }


    if(matrix[0][2]==matrix[1][1] && matrix[1][1]==matrix[2][0]){

        if(matrix[0][2]==1){
            return matrix[0][2];
        } else if(matrix[0][2]==2){
            return matrix[0][2];
        }
    }


    return 0;
}

void insertMatrix(int matrix[][3], int posButton, int a){

    if(posButton == 0){
        matrix[0][0] = a;
    }else if(posButton == 1){
        matrix[0][1] = a;
    }else if(posButton == 2){
        matrix[0][2] = a;
    }else if(posButton == 3){
        matrix[1][0] = a;
    }else if(posButton == 4){
        matrix[1][1] = a;
    }else if(posButton == 5){
        matrix[1][2] = a;
    }else if(posButton == 6){
        matrix[2][0] = a;
    }else if(posButton == 7){
        matrix[2][1] = a;
    }else if(posButton == 8){
        matrix[2][2] = a;
    }

}