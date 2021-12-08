package Main.logic;


public class Heuristic {
	private static int score ;
	public static int evaluate(int [][] state) {
		
		score = 0 ;
		//for each row 
		for(int i=5 ;i>=0 ;i--)
		{
			//count yellow ,red ,empty for the first 4 in the row
			int j,yellow=0,empty=0,red=0 ;
			for(j=0 ;j<4 ;j++)
			{
				if(state[i][j] == 2)
					yellow++ ;
				else if(state[i][j] == 1)
					red++ ;
				else 
					empty++ ;
			}
			
			//score for the first 4
			if(yellow + empty == 4 && red == 0 && empty != 4)
			{
				if(yellow == 4)
					score += 200 ;
				else if (yellow == 3)
					score += 100 ;
				else if(yellow == 2)
					score += 50 ;
				else if(yellow == 1)
					score += 25 ;
			}
			else if(red + empty == 4 && yellow == 0 && empty != 4)
			{
				if(red == 4)
					score -= 200 ;
				else if (red == 3)
					score -= 100 ;
				else if(red == 2)
					score -= 50 ;
				else if(red == 1)
					score -= 25 ;
			}
			
			//score for the remaining elements in row
			for(j=4 ; j<7 ;j++)
			{
				
				//add next element and remove the first one
				int x = state[i][j] ;
				int y = state[i][j-4];
				if(x == 2)
					yellow++ ;
				else if(x == 1)
					red++ ;
				else 
					empty++ ;
				if(y == 2)
					yellow-- ;
				else if(y == 1)
					red-- ;
				else 
					empty-- ;
				
				//score for every 4 tuples
				if(yellow + empty == 4 && red == 0 && empty != 4)
				{
					if(yellow == 4)
						score += 200 ;
					else if (yellow == 3)
						score += 100 ;
					else if(yellow == 2)
						score += 50 ;
					else if(yellow == 1)
						score += 25 ;
				}
				else if(red + empty == 4 && yellow == 0 && empty != 4)
				{
					if(red == 4)
						score -= 200 ;
					else if (red == 3)
						score -= 100 ;
					else if(red == 2)
						score -= 50 ;
					else if(red == 1)
						score -= 25 ;
				}
				
				
			}
		}
		
		
		//for each col
		
		for(int i=0 ;i<7 ;i++)
		{
			//count yellow ,red ,empty for the first 4 in the col
			int j,yellow=0,empty=0,red=0 ;
			for(j=5 ;j>1 ;j--)
			{
				if(state[j][i] == 2)
					yellow++ ;
				else if(state[j][i] == 1)
					red++ ;
				else 
					empty++ ;
			}
			
			//score for the first 4 
			if(yellow + empty == 4 && red == 0 && empty != 4)
			{
				if(yellow == 4)
					score += 200 ;
				else if (yellow == 3)
					score += 100 ;
				else if(yellow == 2)
					score += 50 ;
				else if(yellow == 1)
					score += 25 ;
			}
			else if(red + empty == 4 && yellow == 0 && empty != 4)
			{
				if(red == 4)
					score -= 200 ;
				else if (red == 3)
					score -= 100 ;
				else if(red == 2)
					score -= 50 ;
				else if(red == 1)
					score -= 25 ;
			}
			
			//score for the remaining elements in col
			for(j=1 ; j>=0 ;j--)
			{
				
				//add next element and remove the first one
				int x = state[j][i] ;
				int y = state[j+4][i];
				if(x == 2)
					yellow++ ;
				else if(x == 1)
					red++ ;
				else 
					empty++ ;
				if(y == 2)
					yellow-- ;
				else if(y == 1)
					red-- ;
				else 
					empty-- ;
				
				//score for every 4 tuples
				if(yellow + empty == 4 && red == 0 && empty != 4)
				{
					if(yellow == 4)
						score += 200 ;
					else if (yellow == 3)
						score += 100 ;
					else if(yellow == 2)
						score += 50 ;
					else if(yellow == 1)
						score += 25 ;
				}
				else if(red + empty == 4 && yellow == 0 && empty != 4)
				{
					if(red == 4)
						score -= 200 ;
					else if (red == 3)
						score -= 100 ;
					else if(red == 2)
						score -= 50 ;
					else if(red == 1)
						score -= 25 ;
				}
				
				
			}
		}
		
		
		for(int i=5 ;i>2 ;i--)
		{
			for(int j=0 ;j<7 ;j++)
			{
				if(j+3 < 7)
				{
					int yellow=0 ,red=0 ,empty=0 ;
					for(int k=0 ;k<4 ;k++)
					{
						int x = state[i-k][j+k] ;
						if(x == 2)
							yellow++ ;
						else if(x == 1)
							red++ ;
						else 
							empty++ ;
					}
					if(yellow + empty == 4 && red == 0 && empty != 4)
						{
							if(yellow == 4)
								score += 200 ;
							else if (yellow == 3)
								score += 100 ;
							else if(yellow == 2)
								score += 50 ;
							else if(yellow == 1)
								score += 25 ;
						}
						else if(red + empty == 4 && yellow == 0 && empty != 4)
						{
							if(red == 4)
								score -= 200 ;
							else if (red == 3)
								score -= 100 ;
							else if(red == 2)
								score -= 50 ;
							else if(red == 1)
								score -= 25 ;
						}
				}
				if(j-3 >= 0)
				{
					int yellow=0 ,red=0 ,empty=0 ;
					for(int k=0 ;k>-4 ;k--)
					{
						int x = state[i+k][j+k] ;
						if(x == 2)
							yellow++ ;
						else if(x == 1)
							red++ ;
						else 
							empty++ ;
					}
					if(yellow + empty == 4 && red == 0 && empty != 4){
						if(yellow == 4)
							score += 200 ;
						else if (yellow == 3)
							score += 100 ;
						else if(yellow == 2)
							score += 50 ;
						else if(yellow == 1)
							score += 25 ;
					}
					else if(red + empty == 4 && yellow == 0 && empty != 4)
					{
						if(red == 4)
							score -= 200 ;
						else if (red == 3)
							score -= 100 ;
						else if(red == 2)
							score -= 50 ;
						else if(red == 1)
							score -= 25 ;
					}
				}
			}
		}
		
		return -score ;
	}

//    private static HashSet<String> set = new HashSet<>();
//
//    public static int h(int[][] state) {
//        set.clear();
//        int wins = 3 * 7 + 4 * 6 + 12 * 2;
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 7; j++) {
//                if (state[i][j] == 2) {
//                    check_horizontal(i, j);
//                    check_vertical(i, j);
//                    check_diagonal(i, j);
//                }
//            }
//        }
//        int remove_wins = set.size();
//        wins -= remove_wins;
//        return wins;
//    }
//
//    private static void check_horizontal(int i, int j) {
//        String s;
//        if (j >= 0 && j <= 3) {
//            s = "h" + i + "0";
//            set.add(s);
//        }
//        if (j >= 1 && j <= 4) {
//            s = "h" + i + "1";
//            set.add(s);
//        }
//        if (j >= 2 && j <= 5) {
//            s = "h" + i + "2";
//            set.add(s);
//        }
//        if (j >= 3 && j <= 6) {
//            s = "h" + i + "3";
//            set.add(s);
//        }
//    }
//
//    private static void check_vertical(int i, int j) {
//        String s;
//        if (i >= 0 && i <= 3) {
//            s = "v" + j + "0";
//            set.add(s);
//        }
//        if (i >= 1 && i <= 4) {
//            s = "v" + j + "1";
//            set.add(s);
//        }
//        if (i >= 2 && i <= 5) {
//            s = "v" + j + "2";
//            set.add(s);
//        }
//    }
//
//    private static void check_diagonal(int i, int j) {
//        String s;
//        int right_i = i, left_i = i;
//        int right_j = j, left_j = j;
//        for (int k = 0; k < 4; k++) {
//            if (right_i >= 0 && right_i <= 2 && right_j >= 0 && right_j <= 3) {
//                s = "dr" + right_i + right_j;
//                set.add(s);
//            }
//            right_i--;
//            right_j--;
//        }
//        for (int k = 0; k < 4; k++) {
//            if (left_i >= 0 && left_i <= 2 && left_j >= 3 && left_j <= 6) {
//                s = "dl" + left_i + left_j;
//                set.add(s);
//            }
//            left_i--;
//            left_j++;
//        }
//    }
//
//    public static void main(String[] args) {
//        int[][] state = new int[6][7];
//        Heuristic h = new Heuristic();
//        int a = h.h(state);
//        System.out.println(a);
//    }
}
