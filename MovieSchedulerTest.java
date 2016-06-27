import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MovieSchedulerTest {

	//	Providing test input	
	Movie movies[]={
				  new Movie(0,2,new int[]{10,12,14}),
				  new Movie(1,1,new int[]{11,12,15}),
				  new Movie(2,2,new int[]{8,12,16})
				  };

	//Arranging Expected Output
	public int [][] Availability_Map = {
			{0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0},
			{0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0},
			{0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  1,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0}
			};

	String expected_Schedule="[Movie 3 Showtime :8-10, Movie 1 Showtime :10-12, Movie 2 Showtime :12-13]"; 
	
	//Action
	MovieScheduler testingMovieScheduler=new MovieScheduler();	
	
	
	
	//Testing Generated AvailabilityMap against Expected one   
	@Test
	public void test_AvailabilityMap() {
		

		
		assertEquals(Availability_Map,testingMovieScheduler.Generate_Availability(movies));
		

	}

	//Testing Generated Schedule against Expected one
	@Test
	public void test_GeneratedSchedule() throws IOException {
		
		assertEquals(expected_Schedule,testingMovieScheduler.Scheduler(8));
		

	}

}
