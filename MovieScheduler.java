import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class MovieScheduler 
{
	//Storing each movie information 
	public int [] movieDuration = null;
	public int [][] Availability_Map = null;
	public int num,Movieseen=0;
	public Movie[] Movies;
	public int startHour;

	
	//To store generated schedule of movie shows
	public ArrayList<String> MovieOrder = new ArrayList<String>();
	
	//To track which movie is added to schedule 
	public ArrayList<Integer> MovieCovered = new ArrayList<Integer>();
	
	
	
	//Getting input from user
	public  void getInput()
	{
		System.out.println("Enter Number Of Distinct Movies:");		
		@SuppressWarnings("resource")
		Scanner scn= new Scanner(System.in);
		num=scn.nextInt();
		
		if(num>0)
		{
			
			Availability_Map = new int[num][24];
			//movieDuration = new int[num];
			Movies=new Movie[num];
		
			System.out.println("Enter the Duration of movie:");		
			for(int i=0;i<num;i++)
				{
					System.out.println("Movie "+(i+1));
					Movies[i]=new Movie();
					Movies[i].duration=scn.nextInt();
				}
					
			
			/***************************************************************************
			  Initializing Movie. 
			 ***************************************************************************/

			for(int i=0;i<num;i++)
			{
				System.out.println("Enter the no. of show for Movie "+(i+1));
				int show_count =scn.nextInt();
				Movies[i].show_slots=new int[show_count];
				
				for(int j=0;j<show_count;j++)
				{ 
					System.out.println("Enter the start time of show" + j+"for Movie "+(i+1));
					Movies[i].show_slots[j]=scn.nextInt();
				}
			}
			
			System.out.println("Enter the start time of Akshay kumar");
			 startHour = scn.nextInt();
			
		}
		else
		{
			System.out.println("You entered 0...............Exiting!");		
		}
	
	}

	public  int [][] Generate_Availability(Movie[] movies) {
		// TODO Auto-generated method stub
		Availability_Map = new int[movies.length][24];
		for(int i=0;i<movies.length;i++)
		{
			for(int j=0;j<movies[i].show_slots.length;j++)
				Availability_Map[i][movies[i].show_slots[j]]=1;
		}
		return Availability_Map;
	}

	public String Scheduler(int startHour) throws IOException
	{
		
		while(startHour<24)
		{
			startHour=startHour+Movieseen;
			Movie_Select(startHour);
		}
		System.out.println(MovieOrder.toString());
		return MovieOrder.toString();
	}
	
	/***************************************************************************
	  -: DEVICING SCHEDULE FROM KNOWLEDGE BASE (movieDuration[],Availability_Map[][]) :-
	  
	 ***************************************************************************/

	
	public void Movie_Select(int startHour) throws IOException
	{
		
		ArrayList<Integer> Available_Movie = new ArrayList<Integer>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		
		int [] remaining_Show = new int[num];
		int minIndex = 0;
		
		
		for(int i=0;i<num;i++)
		{
			remaining_Show[i]=0;
		}
		
		for(int i=0;i<num;i++)
		{
			if (MovieCovered.contains(i)) {
				continue;
			} 
			else 
			{
				if(Availability_Map[i][startHour]==1)
				{
					Available_Movie.add(i);
					map.put(i, 0);
				}
					
			}		
		}
		
		
		

		/***************************************************************************
		  -: Decision Block:- If More Than One Movie At a Particular Hour :-
		  
		 ***************************************************************************/
		if(Available_Movie.size()>1)
		{
			
			Iterator<Integer> itr=Available_Movie.iterator();
			for(int j=startHour+1;j<24;j++)
			{
				while(itr.hasNext())
				{
					Object o=itr.next();
					Integer temp=(Integer)o;
					int tempint=temp.intValue();
					if(Availability_Map[tempint][j]==1)
						{remaining_Show[tempint]=remaining_Show[tempint]+1;map.put(tempint,remaining_Show[tempint]);}	
				}
			}

			int min=0,temp=0;

			for (int j = 0; j < num; j++){
				    if (remaining_Show[j]==1 ){
				   minIndex = j;
				   MovieCovered.add(minIndex);
				   MovieOrder.add("Movie "+(minIndex+1)+" Showtime :"+startHour+"-"+(startHour+Movies[minIndex].duration));
			
			 	   Movieseen=Movies[minIndex].duration;
			 	   return;
				   
				  }
				}

				@SuppressWarnings("rawtypes")
				Iterator it = map.entrySet().iterator();

			    while (it.hasNext()) {
			        @SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry)it.next();
			        
			        temp=Movies[(int)pair.getKey()].duration;
			        if(min<temp)
			        {
	        			min=temp;minIndex=(int)pair.getKey();
			        }
			        else
			        {
				        if(min==0)
				        {	min=temp;  	minIndex=(int)pair.getKey();}
			        }
			        
			    }
			MovieCovered.add(minIndex);
			MovieOrder.add("Movie "+(minIndex+1)+" Showtime :"+startHour+"-"+(startHour+Movies[minIndex].duration));
			
			Movieseen=Movies[minIndex].duration;
			
		}
		/***************************************************************************
		 * If only one movie available at a particular hour then allocate movie show *
		 ***************************************************************************/
		else
		{
			if(Available_Movie.size()==1)
			{
				//only movie which is available
				MovieCovered.add(Available_Movie.get(0));
				MovieOrder.add("Movie "+(Available_Movie.get(0)+1)+" Showtime :"+startHour+"-"+(startHour+Movies[Available_Movie.get(0)].duration));
			
				Movieseen=Movies[Available_Movie.get(0)].duration;
			}
		}
		
	}
}


