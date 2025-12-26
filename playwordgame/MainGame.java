import java.util.Scanner;

public class MainGame {
    
  
    @SuppressWarnings("ConvertToStringSwitch")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=====================================");
        System.out.println("     WELCOME TO PLAYWORD GAME     ");
        System.out.println("=====================================\n");
        
        while (true) {
            System.out.println("1. Start New Game");
            System.out.println("2. Exit");
            System.out.print("\nYour choice (1/2): ");
            
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("1")) {
                System.out.println("\nStarting new game...\n");
                Game game = new Game();
                game.play(scanner);
                System.out.println("\nReturning to main menu...\n");
            } 
            else if (choice.equals("2")) {
                System.out.println("\nGoodbye! Thanks for playing!");
                break;
            } 
            else {
                System.out.println("Invalid choice! Please enter 1 or 2.\n");
            }
        }
        
       
    }
}