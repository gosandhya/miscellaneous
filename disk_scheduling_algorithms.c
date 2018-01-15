#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define NUMVALS 100000
#define MAXVAL  5000
#define groupSize 1000


int globalArr [NUMVALS];
int displaynums();
int isCscan = 0;

/*** Start: This part is the code provided by instructor to use for this assignment  ***/

/* initiliazing the struct Node */
struct Node {
  int val;
  struct Node *next;
};

struct Node *head = NULL,*tail = NULL;


/* This method generates a linkedlist of size NUMVALS
First element of the linkedlist is Node head that saves the value
and the pointer to the next Node in the linkedlist. */
void generate() 
{
  struct Node *tofill;
  int insertcount = 0,newcount;
  //count the number of nodes
  int count = 0; 
  head = NULL;

  while (count < NUMVALS) {

    /* assigning space in memory to the node tofill */
    tofill = (struct Node *)malloc(sizeof(struct Node));

    /* generate random number between 0 and MAXVAL and assign it to the node value */
    tofill->val = rand() % MAXVAL; 

    tofill->next=NULL;

    /* initially, if linkedlist is empty (head is null), assign the Node tofill to head. */
    if (head == NULL) 
      {
	       head = tofill;
      }

    /*if head is not null (list not empty), add a node to the end of the list. */
    else 
      {
	       tail->next = tofill;
      }

    tail = tofill;
    count++;

  }
}


/* This function displays the linkedlist. It starts from the head and keep 
traversing to the next until it reaches the end */
int displaynums()
{
  struct Node *x;
  int count = 0;

  x = head;
  while(x) {
    //printf("%d\n",x->val);
    ++count;

    x = x->next;
  }
  //printf("\ncount = %d\n\n",count);
  return(count);

}

/*** End ***/


/*  computing head movement with FCFS algorithm */

int fcfs(struct Node *node)
{

  int disk_group [groupSize];


  int count = displaynums();
  int divideLen = count/groupSize;
  //printf("FCFS: total Number of groups is %d of len %d\n",divideLen,groupSize);

  int arrSize = 0;
  int headMovement=0;
  int nodeCounts =0;
  int totalHeadMovement = 0;
  int diff = 0;


  //calculating head movement by divding the linkedlist in chunks of groupsize : 1000
  while (node != NULL)
    {

      if(arrSize < groupSize)
	{
	  disk_group[arrSize] = node->val;
	  if(arrSize == 0)
	    {
	      diff = abs(disk_group[arrSize]-0);
	      headMovement = headMovement+ diff;
	      //printf("each movement of single group: %d\n\n",diff);
	    }
	  else
	    {
	      diff = abs(disk_group[arrSize] - disk_group[arrSize-1]);
	      headMovement = headMovement + diff;
	      //printf("each movement of single group: %d\n\n",diff);
	    }
	}

      else if(arrSize == groupSize)
	{
	  int temp = disk_group[arrSize-1];
	  arrSize = 0;
	  disk_group[arrSize] = node->val;
	  //printf("array index %d\n",arrSize);
	  //printf("number %d\n", node->val);

	  diff = abs(disk_group[arrSize]-temp);
	  headMovement = headMovement+ diff;
	  //printf("each movement of single group: %d\n\n",diff);
	}

      //totalHeadMovement = totalHeadMovement + headMovement;

      arrSize = arrSize +1;
      node = node->next;
  
    }
  printf("FCFS: Total Head movement: %d\n\n",headMovement);
  //sum of all head movements in each group
  return headMovement;

}

/*This function converts the linkedlist to array in order to implement SCAN and CSCAN*/
void covertListToarray(struct Node *node)
{

  int arrSize = 0;
  while (node != NULL)
    { 
      if(arrSize < NUMVALS)
	{
	  globalArr[arrSize] = node->val;

	}
      arrSize = arrSize +1;
      node = node->next;

    }

}
/*This method is a support method for SCAN and CSCAN algorithm It sorts the array of given range in ascending order*/
void arrySortAscending(int startRange, int intEndRange)
{
  int i=0;
  int j=0;
  int temp=0;

  for(i=startRange; i<=intEndRange; i++)
    {
      for(j=i+1; j<=intEndRange; j++)
        {
	  if(globalArr[i] > globalArr[j])
            {
	      temp     = globalArr[i];
	      globalArr[i] = globalArr[j];
	      globalArr[j] = temp;
            }
        }
    }
}
/*This method is a support method for SCAN and CSCAN algorithm It sorts the array of given range in descending order */
void arrySortDescending(int startRange, int intEndRange)
{
  int i=0;
  int j=0;
  int temp=0;

  for (i = startRange ; i < intEndRange; i++)
    {
      for (j= startRange ; j < intEndRange; j++)
	{
	  if (globalArr[j+1]>globalArr[j])
	    {
	      temp=globalArr[j] ;
	      globalArr[j]=globalArr[j+1] ;
	      globalArr[j+1]=temp ;
	    }
	}
    }
}
/*This function calculates the group head movement for both SCAN and CSCAN*/

int groupHeadMovement(int groupNum, int startRange, int intEndRange)
{
  int groupheadMovement = 0;
  int i=0;
  int j=0;
  int headStart = 0;
  int diff=0;

  if(isCscan == 0)
    {
      if (groupNum % 2 == 0)
	{
	  arrySortAscending(startRange,intEndRange);
	  //groupheadMovement = MAXVAL-1;
	  int temp = startRange;
	  //printf("\nsorted ascending order: ");
	  for(i=startRange; i<intEndRange; i++)
	    {
	      if(i == startRange)
		{
		  diff = abs(globalArr[i]-0);
		  groupheadMovement = groupheadMovement + diff;
		  //printf("array index %d\n",arrSize);
		  //printf("each movement of single group: %d\n\n",diff);
		}
	      else
		{
		  diff = abs(globalArr[i] - globalArr[i-1]);
		  groupheadMovement = groupheadMovement + diff;
		  //printf("each movement of single group: %d\n\n",diff);
		}
	    }
	  //printf("before %d\t", groupheadMovement);
	  if(globalArr[intEndRange-1] != MAXVAL-1)
	    {
	      int end = MAXVAL-1;
	      diff = abs(end - globalArr[intEndRange-1]);    
	      groupheadMovement = groupheadMovement+ diff;
	    }
	  //printf("groupheadMovement %d\t", groupheadMovement);
	  
	}else
	{
	  arrySortDescending(startRange,intEndRange);
	  int temp = startRange;
	  //printf("\nsorted descending order: ");
	  for(i=startRange; i<intEndRange; i++)
	    {
	      if(i == startRange)
		{
		  //printf(" first element %d\n",globalArr[startRange]);
		  int end = MAXVAL-1;
		  diff = abs(end - globalArr[i]);  
		  //printf(" diff %d\n",diff);  
		  groupheadMovement = groupheadMovement + diff;
		  //printf("array index %d\n",arrSize);
		  //printf("each movement of single group: %d\n\n",diff);
		}
	      else
		{
		  diff = abs(globalArr[i] - globalArr[i-1]);
		  groupheadMovement = groupheadMovement + diff;
		  //printf("each movement of single group: %d\n\n",diff);
		}
	    }
	  if(globalArr[intEndRange-1] != 0)
	    {
	      int end = 0;
	      diff = abs(end - globalArr[intEndRange-1]);    
	      groupheadMovement = groupheadMovement+ diff;
	    }
	  
	}
    }else if(isCscan == 1)
    {
      arrySortAscending(startRange,intEndRange);
      
      int temp = startRange;
      //printf("\n sorted ascending order: ");
      for(i=startRange; i<intEndRange; i++)
	{
	  if(i == startRange)
	    {
	      diff = abs(globalArr[i]-0);
	      groupheadMovement = groupheadMovement + diff;
	      //printf("array index %d\n",arrSize);
	      //printf("each movement of single group: %d\n\n",diff);
	    }
	  else
	    {
	      diff = abs(globalArr[i] - globalArr[i-1]);
	      groupheadMovement = groupheadMovement + diff;
	      //printf("each movement of single group: %d\n\n",diff);
	    }
	}
      if(globalArr[intEndRange-1] != MAXVAL-1)
	{
	  int end = MAXVAL-1;
	  diff = abs(end - globalArr[intEndRange-1]);    
	  groupheadMovement = groupheadMovement+ diff;
	} 
      
      groupheadMovement = groupheadMovement*2;

      //printf("CSCAN groupheadMovement %d\t", groupheadMovement);

    }
  return groupheadMovement;

}
int scan(struct Node *node)
{
  isCscan = 0;

    
  int count = displaynums();
 

  covertListToarray(node);
  int groupNum = count/groupSize;
  //printf("SCAN: total Number of groups is %d of len %d\n",groupNum,groupSize);
  int group = 0;
  int arrSize =0;
  int totalHeadMovement = 0;

  while(group < groupNum && arrSize != NUMVALS)
    {
      if(arrSize == 0)
	{
	  int start = group*arrSize;
	  int end = groupSize-1;

	  int headMove = groupHeadMovement(group, start,end);

	  totalHeadMovement = totalHeadMovement+headMove;
	  //printf(" from method %d\n",headMove);
	}
      else
	{
	  int start = group*arrSize;
	  int end = ((group+1) * arrSize)-1;
	  int headMove = groupHeadMovement(group, start,end);

	  totalHeadMovement = totalHeadMovement +headMove;
	  //printf("from method %d\n",headMove);

	}
      
      group = group + 1;
      arrSize=groupSize;

    }

  printf("SCAN: Total HeadMovement %d\n\n",totalHeadMovement);

  return totalHeadMovement;

}

int C_scan(struct Node *node)
{
  isCscan = 1;
  //generate();
  int count = displaynums();
  covertListToarray(node);
  int groupNum = count/groupSize;
  //printf("CSCAN: total Number of groups is %d of len %d\n",groupNum,groupSize);
  int group = 0;
  int arrSize =0;
  int totalHeadMovement = 0;

  while(group < groupNum && arrSize != NUMVALS)
    {
      if(arrSize == 0)
	{
	  //printf(" %d %d %d\n",group, group*arrSize, groupSize-1);
	  int headMove = groupHeadMovement(group, group*arrSize,groupSize-1);
	  totalHeadMovement = totalHeadMovement+headMove;
	}
      else
	{
	  /*printf(" %d %d %d\n",group, group*arrSize, ((group+1) * arrSize)-1);*/
	  int headMove = groupHeadMovement(group, group*arrSize,((group+1) * arrSize)-1);
	  totalHeadMovement = totalHeadMovement +headMove;
	}
      
      group = group + 1;
      arrSize=groupSize;

    }
  printf("CSCAN: Total HeadMovement %d\n\n",totalHeadMovement);
  return totalHeadMovement;
}

int main( int argc, char *argv[] ) 
{


  srand( (int)time(NULL) );

  int Avg_FIFOHeadMovement =0;
  int Avg_SCANHeadMovement =0;
  int Avg_CSCANHeadMovement =0;
  int i=0;
  for(i=0; i<3; i++)
    {
      generate();
      Avg_FIFOHeadMovement+=fcfs(head);
      Avg_SCANHeadMovement+=scan(head);
      Avg_CSCANHeadMovement+=C_scan(head);
    }

  printf("SUM FIFO Head Movement %d\n", Avg_FIFOHeadMovement );
  Avg_FIFOHeadMovement = Avg_FIFOHeadMovement/3;
  printf("avg FIFO Head Movement %d\n", Avg_FIFOHeadMovement );

  printf("SUM SCAN Head Movement %d\n", Avg_SCANHeadMovement );
  Avg_SCANHeadMovement = Avg_SCANHeadMovement/3;
  printf("avg SCAN Head Movement %d\n", Avg_SCANHeadMovement );


  printf("SUM CSCAN Head Movement %d\n", Avg_CSCANHeadMovement );
  Avg_CSCANHeadMovement = Avg_CSCANHeadMovement/3;
  printf("avg CSCAN Head Movement %d\n", Avg_CSCANHeadMovement );

}
