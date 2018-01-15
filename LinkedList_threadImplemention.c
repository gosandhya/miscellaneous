#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>


pthread_mutex_t count_mutex     = PTHREAD_MUTEX_INITIALIZER;

struct Node *head = NULL;
struct Node *end = NULL;

int numCount = 0;
int maxBound = 0;

int deletedNodeCount=0;
int turn =0;
int firstTime = 0;


int perfectList = 0;
struct Node
{
  int randomNum;
  struct Node *next;
};


// This function prints contents of linked list starting from head
void printList(struct Node *node)
{
  while (node != NULL)
  {
     printf("%d ", node->randomNum);
     node = node->next;
  }
  printf("\n");
}


//this function inserts the nodes at the end of linkedlist.

void *insertatend()
{

     while(!perfectList)
     {
      //if it is thread 1 turn
      if(turn == 0)
      {
       // printf("I am in insert and it is my turn %d\n",turn);
    
      int i=0;
      pthread_mutex_lock(&count_mutex);

      //for the first time, I am inserting the number of nodes user specified, else I am only inserting 
      //the number of nodes that were deleted by second thread
      if(firstTime == 0)
      {
      for(i =0; i<numCount; i++)
      {
        struct Node *temp = (struct Node*)malloc(sizeof(struct Node));

       int data = rand() % maxBound;
  
       temp->randomNum= data;
       temp->next=NULL;

      if(head==NULL)
      {
        head=temp;
        end=temp;
      }
      end->next=temp;

      //Making last node point to newly created temp node
      end=temp; 

      }
      firstTime = 1;
    }
    else
    {
      for(i =0; i<deletedNodeCount; i++)
      {
        struct Node *newNode = (struct Node*)malloc(sizeof(struct Node));
        struct Node* temp;

        if( newNode == NULL )
        {
          //printf("memory not allocated");
        }

        else
        {
          int data = rand() % maxBound;

          newNode->randomNum = data; // Link the data part
          newNode->next = NULL; 

          temp = head;

        // Traverse to the last node
          while(temp->next != NULL){
            temp = temp->next;}
            temp->next = newNode; // Link address part

        }

      }
    }
      printf("I am in insert and here is the list\n");
      printList(head);
      turn =1;
      pthread_mutex_unlock( &count_mutex );

    }
    } 
     return NULL;

}
    
 void *removeAdjacentNode()
  {

    /* Pointer to store the next pointer of a node to be deleted*/
    struct Node* nextNode; 
    while(!perfectList)
    {
      if(turn == 1)
      {

     /* Pointer to traverse the linked list */

    int deleteCount=0;
    struct Node* current = head;
    pthread_mutex_lock( &count_mutex );


      if (current == NULL)
      {  return NULL; }

    while (current != NULL && current->next != NULL) 
    {

       if (abs(current->randomNum - current->next->randomNum) < 8) 
       {
        struct Node* temp = current->next;
        current->next = temp->next;
        temp->next = NULL;
        free(temp);

        deleteCount+=1;
        
       }
       else /* This is tricky: only advance if no deletion */
       {  current = current->next; 
       }
       
    }

    deletedNodeCount= deleteCount;

    if(deletedNodeCount == 0)
    {
      perfectList = 1;
    }

    turn =0;


    printf("I am in delete and no of nodes deleted are %d\n",deletedNodeCount);
    printf("list after deletion\n");
    printList(head);

    pthread_mutex_unlock( &count_mutex );

  }

}


 // printf("perfect list count%d\n",globalCount);

    return NULL;
} 


int main( int argc, char *argv[] ) 
 {


  if(argc == 3)
    {

      pthread_t thread1;
      pthread_t thread2;

      //save the user input in variables                                                                                                                                   
       numCount = atoi (argv[1]);
       maxBound = atoi(argv[2]);

       int err1;
       int err2;

       //this line makes sure that there are different random integers everytime the program runs                                                                           
      srand ( time(NULL) );
      printf("Count of number is %d and maximum bound is %d\n", numCount, maxBound);
 

       err1 = pthread_create(&thread1, NULL, &insertatend, NULL);
       
        if (err1 != 0)
        {printf("\ncan't create thread :[%s]", strerror(err1));}  
        else
        {printf("\n Thread 1 created successfully\n");}
      
        err2 = pthread_create(&thread2, NULL, &removeAdjacentNode, NULL);
        
        if (err2 != 0)
        {printf("\ncan't create thread :[%s]", strerror(err2));}
        else
        {printf("\n Thread 2 created successfully\n");}

        pthread_join(thread1, NULL);  /*Wait until thread is finished */
        pthread_join(thread2, NULL);  /*Wait until thread is finished */


        printf("Final list is \n");
        printList(head);
       // printf("my global count%d\n", globalCount);
        exit(EXIT_SUCCESS);
      
    }

  else
    {
      printf("please specify count of numbers and maximum length.\n");
      printf("perfect list%d\n",perfectList);
      exit(0);
    }

}


