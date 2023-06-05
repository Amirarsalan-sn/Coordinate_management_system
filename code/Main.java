import java.util.Scanner;

public class Main {
    private static Bank max;
    private static List maxList = new List();
    private static boolean isMaxChanged = false;
    private static TwoDTree universal = new TwoDTree();
    private static TrieTreeB mainBanks = new TrieTreeB();
    private static TrieTreeN neighborhoods = new TrieTreeN();
    private static final String regex = "[a-z0-9]+";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] command;
        System.out.println("""
                Welcome :) .
                                
                would you like to read the user manual ?? (y/N)
                """);
        if (scanner.nextLine().equals("y")) {
            System.out.println("""
                    Hi :)
                                        
                                        
                    1) you can enter any real pair of numbers as coordinates .
                    2) coordinates and radius are in Kilometers .
                    3) every bank and every neighborhood has a "Distinct" name which can only consist of small letters and numbers .
                        Otherwise the name would be Invalid ! .
                    
                    4) if you wanted to add a new neighborhood , just insert the min and max coordinates of it :
                        
                                                        (max)
                                                         /
                              *-------------------------*
                              |                         |
                              |                         |
                              |                         |
                              *-------------------------*
                             /
                          (min)
                          
                          
                          OR :
                                         (max)
                                          /
                                *--------*
                                |        |
                                |        |
                                |        |
                                |        |
                                |        |
                                |        |
                                *--------*
                               /
                            (min)
                            
                                    
                    5) Enter your commands in format which is declared below :
                                        
                        >> addN Nname (first element of min) (second element of min)  (max(like min))
                            example : addN grovestreet 10 20 30 40
                        >> addB Bname X Y(coordinates)
                            example : addB mellat 0 4.23
                        >> addBr Bname Brname X Y(coordinates) NOTE : you can't add a branch office unless you've already added it's head office .
                            example : addBr mellat shobe23 44 -23.5
                        >> delBr X Y(coordinates)
                        >> listB Nname
                            example : listB javadie
                        >> listBrs Bname
                            example : listBr mellat
                        >> nearB X Y(your desired location)
                        >> nearBr Bname X Y(your desired location)
                            example : nearBr mellat 44 72
                        >> availB X Y(your desired location) R(radius)
                            example : availB 1 0.34 0.3
                        >> mostBrs
                        >> exit
                        
                        
                    """);
        }

        System.out.println("You can enter your commands :");
        double[] coo = new double[4];
        String s;
        Loop:
        while (true) {
            command = scanner.nextLine().trim().split(" ");
            switch (command[0]) {
                case "addN":
                    if(command.length == 6) {
                        if (command[1].matches(regex)) {
                            try {
                                for (int i = 2; i < 6; i++) {
                                    coo[i - 2] = Double.parseDouble(command[i]);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid coordinates ! try again . ");
                                break;
                            }
                            if(coo[3] > coo[1] && coo[2] > coo[0]) {
                                Neighborhood newN = new Neighborhood(command[1], coo[0], coo[1], coo[2], coo[3]);
                                neighborhoods.add(neighborhoods.root, newN, 0);
                            } else {
                                System.out.println("Invalid neighborhood enter min and max correctly !");
                            }
                        } else
                            System.out.println("Invalid name ! try again .");
                    } else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break;
                case "addB":
                    if(command.length == 4) {
                        if (command[1].matches(regex)) {
                            try {
                                for (int i = 2; i < 4; i++) {
                                    coo[i - 2] = Double.parseDouble(command[i]);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid coordinates ! try again . ");
                                break;
                            }
                            Bank newB = new Bank(coo[0], coo[1], command[1]);
                            if (max == null) {
                                max = newB;
                            }
                            if (mainBanks.add(mainBanks.root, newB, 0)) {
                                universal.add(newB, universal.root, 0);
                                maxList.add(newB);
                            }
                        } else {
                            System.out.println("Invalid name ! try again .");
                        }
                    } else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break;
                case "addBr":
                    if(command.length == 5) {
                        if (command[1].matches(regex) && command[2].matches(regex)) {
                            try {
                                for (int i = 3; i < 5; i++) {
                                    coo[i - 3] = Double.parseDouble(command[i]);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid coordinates ! try again .");
                                break;
                            }
                            Bank bank = mainBanks.find(mainBanks.root, command[1], 0);
                            if (bank != null) {
                                Branch newBr = new Branch(coo[0], coo[1], command[1], command[2]);
                                if (universal.add(newBr, universal.root, 0)) {
                                    bank.branches.add(newBr, bank.branches.root, 0);
                                    if (bank.branchesSize() > max.branchesSize()) {
                                        max = bank;
                                    }
                                }
                            } else {
                                System.out.println("The head office does not exist ! first add its head office .");
                            }
                        } else {
                            System.out.println("Invalid names ! try again .");
                        }
                        break;
                    }else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "delBr":
                    if(command.length == 3) {
                        try {
                            for (int i = 1; i < 3; i++) {
                                coo[i - 1] = Double.parseDouble(command[i]);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid coordinates ! try again .");
                            break;
                        }
                        Point point = new Point(coo[0], coo[1]);
                        Bank br = universal.delete(point, universal.root, 0);
                        if (br != null) {
                            br = mainBanks.find(mainBanks.root, br.name, 0);
                            br.branches.delete(point, br.branches.root, 0);
                            if (max == br) {
                                isMaxChanged = true;
                            }
                        }

                        break;
                    }else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "listB":
                    if (command.length == 2) {
                        if (command[1].matches(regex)) {
                            Neighborhood neighborhood = neighborhoods.find(neighborhoods.root, command[1], 0);
                            if (neighborhood != null) {
                                s = universal.availBanks(neighborhood, universal.root, 0).trim();
                                System.out.println(s.equals("") ? "Nothing !" : s);
                            } else {
                                System.out.println("Neighborhood " + command[1] + " does not exist !");
                            }
                        } else {
                            System.out.println("Invalid neighborhood name ! try again .");
                        }
                        break;
                    }else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "listBrs":
                    if(command.length == 2) {
                        if (command[1].matches(regex)) {
                            Bank bank = mainBanks.find(mainBanks.root, command[1], 0);
                            if (bank != null) {
                                s = bank.branches.toString(bank.branches.root, new StringBuilder()).trim();
                                System.out.println(s.equals("")? "Nothing !" : s);
                            } else {
                                System.out.println("Bank : " + command[1] + " does not exist !");
                            }
                        } else {
                            System.out.println("Invalid head office name ! try again .");
                        }
                        break;
                    } else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "nearB":
                    if(command.length == 3) {
                        try {
                            for (int i = 1; i < 3; i++) {
                                coo[i - 1] = Double.parseDouble(command[i]);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid coordinates ! try again");
                            break;
                        }
                        KDNode found = universal.nearestN(new Point(coo[0], coo[1]), universal.root, 0);
                        if (found != null)
                            System.out.println(found.data);
                        else
                            System.out.println("No near bank found !");
                        break;
                    } else {
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "nearBr":
                    if(command.length == 4) {
                        if (command[1].matches(regex)) {
                            try {
                                for (int i = 2; i < 4; i++) {
                                    coo[i - 2] = Double.parseDouble(command[i]);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid coordinates ! try again .");
                                break;
                            }
                            Bank bank = mainBanks.find(mainBanks.root, command[1], 0);
                            if (bank != null) {
                                KDNode foundBr = bank.branches.nearestN(new Point(coo[0], coo[1]), bank.branches.root, 0);
                                if (foundBr != null)
                                    System.out.println(foundBr.data);
                                else
                                    System.out.println("no near bank found !");
                            } else {
                                System.out.println("The head office : " + command[1] + " does not exist !");
                            }
                        } else {
                            System.out.println("Invalid Bank name ! try again .");
                        }
                        break;
                    }else{
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "availB":
                    if(command.length == 4) {
                        try {
                            for (int i = 1; i < 4; i++) {
                                coo[i - 1] = Double.parseDouble(command[i]);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid coordinates or Invalid radius ! try again .");
                            break;
                        }
                        s = universal.availBanks(new Point(coo[0], coo[1]), universal.root, 0, coo[2]*coo[2]).trim();
                        System.out.println(s.equals("") ? "Nothing !" : s);
                        break;
                    }else{
                        System.out.println("Invalid command format (maybe an Invalid name exists or parameters are more/less than required) ! try again .");
                    }
                    break ;
                case "mostBrs":
                    if (isMaxChanged) {
                        max = maxList.max();
                        isMaxChanged = false;
                    }
                    if(max != null)
                        System.out.println(max);
                    else
                        System.out.println("nothing !");
                    break;
                case "exit":
                    System.out.println("Bye Bye");
                    break Loop;
                default:
                    System.out.println("Invalid command ! try again .");
            }
        }
    }
}

class List {
    private int counter = 0;
    private Bank[] banks = new Bank[30];
    private int size = 30;

    public void add(Bank bank) {
        if (counter == (size - 1)) {
            doubleArray();
        }
        banks[counter++] = bank;
    }

    public Bank max() {
        Bank max = null ;
        for (int i = 0; i < counter; i++) {
            if(i == 0 || max.branchesSize() < banks[i].branchesSize()){
                max = banks[i];
            }
        }
        return max;
    }

    private void doubleArray() {
        Bank[] nArr = new Bank[2 * size];
        int tempC = 0;
        for (Bank bank :
                banks) {
            nArr[tempC++] = bank;
        }

        banks = nArr;
        size *= 2;
    }


}
// 900 lines of code