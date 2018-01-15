#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>
#include <stdbool.h>

pthread_mutex_t count_mutex  = PTHREAD_MUTEX_INITIALIZER;

struct Node *head = NULL;
struct Node *head2 = NULL;
struct Node *end = NULL;

int numCount = 0;
int maxBound = 0;

int deletedNodeCount=0;
int turn =0;
int firstTime = 0;
int firstTimeInDelete=0;
int perfectList = 0;

int FrameInMemory [16];
int FrameInMemory_twod [16][2];

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
      printf("%d  ", node->randomNum);
      node = node->next;
    }
  printf("\n");
}


//This function copies the original linkedlist
void copy(struct Node *list)
{
  //struct Node *newList = NULL;
  struct Node **newIt = &head2;

  while(list!=NULL)
    {
      *newIt = malloc(sizeof(struct Node));
      memcpy(*newIt,list,sizeof(struct Node));

      list = list->next;
      newIt = &((*newIt)->next);
    }
  //return head2;
}



//insert at the end in the linkelist
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
	  else{
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
	  //printf("I am in insert and here is the list\n");
	  //printList(head);
	  turn =1;
	  pthread_mutex_unlock( &count_mutex );

	}
    } 
  return NULL;

}



//remove adjacent duplicate element from the linkedlist
void *removeDuplicateNode()
{

  /* Pointer to store the next pointer of a node to be deleted*/
  struct Node* nextNode; 
    
  while(!perfectList)
    {
      if(turn == 1)
	{
          if(firstTimeInDelete == 0)
	    {
	      copy(head);
	      firstTimeInDelete= 1;
	    }

	  /* Pointer to traverse the linked list */
	  int deleteCount=0;
	  struct Node* current = head;
	  pthread_mutex_lock( &count_mutex );
	  if (current == NULL)
	    {  return NULL; }

	  while (current != NULL && current->next != NULL) 
	    {
	      if (current->randomNum == current->next->randomNum) 
		{
		  struct Node* temp = current->next;
		  current->next = temp->next;
		  temp->next = NULL;
		  free(temp);
		  deleteCount+=1;
		}
	      else /* This is tricky: only advance if no deletion */
		{  current = current->next; }
       
	    }

	  deletedNodeCount= deleteCount;

	  if(deletedNodeCount == 0)
	    {
	      perfectList = 1;
	    }

	  turn =0;
	  pthread_mutex_unlock( &count_mutex );

	}

    }
  return NULL;
} 



//print 1-d array
void printArray(int arr[])
{
  int i=0;
  for(i=0; i<16; i++)
    {
      printf("%d ", arr[i]);
    }
}
//print 2-d array
void printTwodArray(int arr[16][2])
{
  int i=0;
  int j=0;
  for(i=0; i<16; i++)
    {
      for(j=0; j<2; j++)
	{
	  printf("%d ", arr[i][j]);
	}
    }
}
//This method checks whether the page is already in the main memory
int checkValInarr(struct Node *node)
{
  int i =0;
  int answer =0;
  while(i<16)
    {
      if (node->randomNum == FrameInMemory[i])
	{
	  answer = 1;
	}
      i++;
    }
  return answer;
}
//FIFO page replacement 
double FIFOPageReplacement(struct Node *node)
{

  int arrSize = 0;
  double pageFault=0;
  double nodeCounts =0;
  while (node != NULL)
    {
      int a = checkValInarr(node);
    
      if(arrSize < 16 )
	{
	  //printf("printing a before %d\n",a);
	  if(a==0)
	    {
	      FrameInMemory[arrSize] = node->randomNum;
	      //printf("%d ", node->randomNum);
	      arrSize = arrSize +1;
	      pageFault = pageFault+1;
	    }
     
	}
      else
	{
	  int a = checkValInarr(node);
      
	  if(a == 0)
	    {

	      //here I am removing first element, shifting the entire array towards left to add an space for next page
	      memmove(&FrameInMemory[0], &FrameInMemory[1], (arrSize) * sizeof(FrameInMemory[0]));
	      FrameInMemory[arrSize-1] = node->randomNum;
	      pageFault = pageFault+1;
	    }
 
	}
      node = node->next;
      nodeCounts = nodeCounts+1;
    }

  //printArray(FrameInMemory);
  //printf("\n");

  //printf("******** FIFO **********\n");

  //printf("Node counts are %0.4lf\n", nodeCounts);
  //printf("Page Faults are %.4lf\n", pageFault);
  //printf("Page Fault Rate %.4lf\n", pageFault/nodeCounts);

  return pageFault/nodeCounts;

}


//This method is used for optimal page replacement
int CheckRefString(struct Node *temp, int n)
{

  // this method checks each page frame in the reference string and returns the distance
  // the higher the distance is, the higher the chances of that page to be replaced

  int stepCount =0;

  //printf("Starting Node  %d\n", temp->randomNum);
  while(temp!= NULL && n != temp->randomNum)
    {
      stepCount=stepCount+1;
      temp = temp->next;
    }
  //printf("method %d %d\n", n, stepCount);
  return stepCount;

}


//This method is also used for optimal page replacement and returns the maximum distance 
int findMaxVal(int arr[])
{
  int max=0;
  int i=0;
  for (i=1; i<16; i++)
    {
      if (arr[i] > max)
	{
          max = arr[i];
	}
       
    }
  return max;

}

//This method gets the index of the page with maximum distance.
int get_index(int a[], int value)
{
  int index=0;

  while(a[index] != value)
    {
      index++;
    }
  return index;
}
//Optimal Page replacement
double OPTIMALPageReplacement(struct Node *node)
{
  int arrSize = 0;
  double pageFault=0;
  double nodeCounts =0;
  while (node != NULL)
    {
      int a = checkValInarr(node);
    
      if(arrSize < 16 )
	{
	  //printf("printing a before %d\n",a);
	  if(a==0)
	    {
	      FrameInMemory[arrSize] = node->randomNum;
	      //printf("%d ", node->randomNum);
	      arrSize = arrSize +1;
	      pageFault = pageFault+1;
	    }
	}
      else
	{
	  int a = checkValInarr(node);
	  //printf("printing a %d\n",a);
	  if(a == 0)
	    {

	      struct Node* temp = node;
	      int tempArr[16];
	      int maxVal =0;
              int i=0;
	      for(i=0; i<16; i++)
		{
		  int val1 = CheckRefString(temp,FrameInMemory[i]);
		  tempArr[i] = val1;
		}
	      maxVal = findMaxVal(tempArr);
	      printf("");
	      int index = get_index(tempArr, maxVal);

	      //printf("replacing the value at index %d\n", index);
	      FrameInMemory[index] = node->randomNum;

	      pageFault = pageFault+1;
     
	    }
 
	}
      node = node->next;
      nodeCounts = nodeCounts+1;
    
      // printArray(FrameInMemory);
      //printf("\n");

    }

  //printf("******** OPTIMAL **********\n");
  //printf("Node counts are %0.4lf\n", nodeCounts);
  //printf("Page Faults are %.4lf\n", pageFault);
  //printf("Page Fault Rate %.4lf\n", pageFault/nodeCounts);

  return pageFault/nodeCounts;


}



//This method is used for LRU page replacement. I am using 2D array for LRU page replacement. 
//This method checks whether the page is already in the memory (array)
int checkValInarr_twod(struct Node *node)
{
  int i =0;
  int answer =0;
  while(i<16)
    {
      if (node->randomNum == FrameInMemory_twod[i][0])
	{
	  answer = 1;
	}

      i++;
    }
  return answer;
}

//This method returns the index of minimum value assigned to each page. 
//We want to replace the page with minimum index
int findMinVal(int arr[16][2])
{

  int minimum = 10000;
  int index =0;
  int c=0;
  for ( c = 0 ; c < 16 ; c++ ) 
    {
      if ( arr[c][1] < minimum ) 
        {
	  minimum = arr[c][1];
	  index = c;
	  //index = c+1;
        }
    }
  //printf("Minimum element is present at location %d and it's value is %d.\n", index, minimum);
  return index;

}


//LRU page replacement
double LRUPageReplacement(struct Node *node)
{

  int arrSize = 0;
  double pageFault=0;
  double nodeCounts =0;
  int arrSizeCounter=0;

  while (node != NULL)
    {
      int a = checkValInarr_twod(node);

      if(arrSize < 16)
	{
	  if(a==0)
	    {
	      FrameInMemory_twod[arrSize][0]= node->randomNum;
	      FrameInMemory_twod[arrSize][1] = arrSize;
	      arrSize = arrSize +1;      
	      pageFault = pageFault+1;
    
	    }

	  arrSizeCounter = arrSize;
	}
      else
	{
      
	  a = checkValInarr_twod(node);
	  //printf("printing a %d\n",a);
	  if(a == 0)
	    {
	      int minValIndex = findMinVal(FrameInMemory_twod);
	      //printf("index of min value of arr %d\n", minValIndex);

	      //int index = get_index(tempArr, maxVal);

	      //printf("index of max Val %d\n", index);
	      FrameInMemory_twod[minValIndex][0] = node->randomNum;
	      FrameInMemory_twod[minValIndex][1] = arrSizeCounter;

	      pageFault = pageFault+1;
	      arrSizeCounter = arrSizeCounter+1;
     
	    }
	}
 
      node = node->next;
      nodeCounts = nodeCounts+1;

      //printTwodArray(FrameInMemory_twod);
      //printf("\n");

    }

  //printf("******** LRU **********\n");
  //printf("Node counts are %0.4lf\n", nodeCounts);
  //printf("Page Faults are %.4lf\n", pageFault);
  //printf("Page Fault Rate %.4lf\n", pageFault/nodeCounts);

  return pageFault/nodeCounts;
}
int main( int argc, char *argv[] ) 
{
  pthread_t thread1;
  pthread_t thread2;

  //For the list of 1000 numbers  between 0-64

  numCount = 1000;
  maxBound = 64;

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
  err2 = pthread_create(&thread2, NULL, &removeDuplicateNode, NULL);
  if (err2 != 0)
    {printf("\ncan't create thread :[%s]", strerror(err2));}
  else
    {printf("\n Thread 2 created successfully\n");}

  /*Wait until thread is finished */
  pthread_join(thread1, NULL); 
  pthread_join(thread2, NULL);  /*Wait until thread is finished */


  printf("*****************\n");
  printf("Unculled list between 0 - 64: \n");
  printf("-------------------\n");
  printList(head2);

  printf("Culled list between 0 - 64: \n");
  printf("-------------------\n");
  printList(head);


  double Culled_avgFIFOFaultRate =0;
  double Culled_avgOptimalFaultRate =0;
  double Culled_avgLRUFaultRate = 0;

  double Unculled_avgFIFOFaultRate =0;
  double Unculled_avgOptimalFaultRate =0;
  double Unculled_avgLRUFaultRate = 0;

  int i=0;
  for( i=0; i<3; i++)
    {

      Culled_avgFIFOFaultRate+= FIFOPageReplacement(head);
      Culled_avgOptimalFaultRate+=OPTIMALPageReplacement(head);
      Culled_avgLRUFaultRate +=LRUPageReplacement(head);

      Unculled_avgFIFOFaultRate+= FIFOPageReplacement(head2);
      Unculled_avgOptimalFaultRate+=OPTIMALPageReplacement(head2);
      Unculled_avgLRUFaultRate +=LRUPageReplacement(head2);

    }

       
  printf("******** 64 - Unculled FIFO: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Unculled_avgFIFOFaultRate/3);

  printf("******** 64 - Unculled OPTIMAL: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Unculled_avgOptimalFaultRate/3);

  printf("******** 64 - Unculled LRU: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Unculled_avgLRUFaultRate/3);

   

  printf("******** 64 - Culled FIFO: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Culled_avgFIFOFaultRate/3);

  printf("******** 64 - Culled OPTIMAL: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Culled_avgOptimalFaultRate/3);

  printf("******** 64 - Culled LRU: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Culled_avgLRUFaultRate/3);


  // same routine for list between 0 - 32

  pthread_t thread3;
  pthread_t thread4;

  //initializing the variables again                                                                                                            
  numCount = 1000;
  maxBound = 32;
  firstTime = 0;
  firstTimeInDelete =0;
  perfectList =0;
  deletedNodeCount =0;
  turn =0;

  head = NULL;
  head2  = NULL;
  end = NULL;


  int err3;
  int err4;

  //this line makes sure that there are different random integers everytime the program runs                                                                           
  srand ( time(NULL) );
  printf("Count of number is %d and maximum bound is %d\n", numCount, maxBound);
  err3 = pthread_create(&thread3, NULL, &insertatend, NULL);
  if (err3 != 0)
    {printf("\ncan't create thread :[%s]", strerror(err3));}  
  else
    {printf("\n Thread 1 created successfully\n");}
  err4 = pthread_create(&thread4, NULL, &removeDuplicateNode, NULL);
  if (err4 != 0)
    {printf("\ncan't create thread :[%s]", strerror(err4));}
  else
    {printf("\n Thread 2 created successfully\n");}

  /*Wait until thread is finished */
  pthread_join(thread3, NULL); 
  pthread_join(thread4, NULL);  /*Wait until thread is finished */


  printf("*****************\n");
  printf("Unculled list between 0 - 32: \n");
  printf("-------------------\n");
  printList(head2);


  printf("Culled list between 0 - 32: \n");
  printf("-------------------\n");
  printList(head);



  Culled_avgFIFOFaultRate =0;
  Culled_avgOptimalFaultRate =0;
  Culled_avgLRUFaultRate = 0;

  Unculled_avgFIFOFaultRate =0;
  Unculled_avgOptimalFaultRate =0;
  Unculled_avgLRUFaultRate = 0;

  i=0;
  for( i=0; i<3; i++)
    {

      Culled_avgFIFOFaultRate+= FIFOPageReplacement(head);
      Culled_avgOptimalFaultRate+=OPTIMALPageReplacement(head);
      Culled_avgLRUFaultRate +=LRUPageReplacement(head);
      Unculled_avgFIFOFaultRate+= FIFOPageReplacement(head2);
      Unculled_avgOptimalFaultRate+=OPTIMALPageReplacement(head2);
      Unculled_avgLRUFaultRate +=LRUPageReplacement(head2);

    }

  printf("******** 32 - Unculled FIFO: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Unculled_avgFIFOFaultRate/3);

  printf("******** 32 -Unculled OPTIMAL: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Unculled_avgOptimalFaultRate/3);

  printf("******** 32 -Unculled LRU: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Unculled_avgLRUFaultRate/3);

   
  printf("******** 32 - Culled FIFO: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Culled_avgFIFOFaultRate/3);

  printf("******** 32 - Culled OPTIMAL: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Culled_avgOptimalFaultRate/3);

  printf("******** 32 - Culled LRU: AVG PAGE FAULT **********\n");
  printf("%.4lf\n", Culled_avgLRUFaultRate/3);

  return 0;

}
