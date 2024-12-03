package auction_system;

import javafx.scene.control.TextArea;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class FileManager {

    private CategoryController categoryController;
    private CommissionController commissionController;
    private PremiumController premiumController;

    public void saveRegisteredUserData(String username, TextArea itemListArea) {
        try (FileWriter writer = new FileWriter("registered_user_data.txt", true)) {
            String [] lines = itemListArea.getText().split("\n");
            for (String line : lines) {
                writer.write(username + ": " + line);
                writer.write("\n");
            }
            showAlert("Data Saved Successfully", "Data saved successfully to registered_user_data.txt");
        } catch (Exception e) {
            showAlert("File Error", "Error saving data to file.");
            e.printStackTrace();
        }
    }

    public void loadRegisteredUserData(String username, TextArea itemListArea) {
        File file = new File("registered_user_data.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(username + ":")) {
                        itemListArea.appendText(line.substring((username + ":").length()) + "\n");
                    }
                }
            } catch (IOException e) {
                showAlert("File Error", "Error loading data from file.");
                e.printStackTrace();
            }
        } else {
            showAlert("File Not Found", "No data file found for username: " + username);
        }
    }

    public void saveAdminData(List<String> categories, double sellerCommission, double buyerPremium) {
        try (FileWriter writer = new FileWriter("admin_data.txt")) {
            writer.write("Categories\n");
            for (String category : categories) {
                writer.write("- " + category + "\n");
            }
            writer.write("\nSeller Commission: ");
            writer.write(sellerCommission + "\n");
            writer.write("Buyer Premium: ");
            writer.write(buyerPremium + "\n");

            showAlert("Data Saved Successfully", "Data saved successfully to admin_data.txt");
        } catch (Exception e) {
            showAlert("File Error", "Error saving data to file.");
            e.printStackTrace();
        }
    }

    public void loadAdminData(CategoryController categoryController, CommissionController commissionController, PremiumController premiumController) {
        File file = new File("admin_data.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean inCategories = false;

                while ((line = reader.readLine()) != null) {
                    if (line.equals("Categories")) {
                        inCategories = true;
                        continue;
                    }
                    if (inCategories) {
                        if (line.startsWith("- ")) {
                            String category = line.substring(2).trim();
                            categoryController.addCategory(category);
                        } else {
                            inCategories = false;
                        }
                    }

                    if (line.startsWith("Seller Commission:")) {
                        double commission = Double.parseDouble(line.split(":")[1].replace("%", "").trim());
                        commissionController.setSellerCommission(commission);
                    } else if (line.startsWith("Buyer Premium:")) {
                        double premium = Double.parseDouble(line.split(":")[1].replace("%", "").trim());
                        premiumController.setBuyerPremium(premium);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                showAlert("File Error", "Error loading data from file.");
                e.printStackTrace();
            }
        }
    }

    public static boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials.length > 0 && credentials[0].trim().equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // File not found is acceptable if it hasn't been created yet
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void saveCredentials(String username, String password, String userType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt", true))) {
            writer.write(username + ":" + password + ":" + userType);
            writer.newLine();
        } catch (Exception e) {
            showAlert("File Error", "Error saving credentials to file.");
            e.printStackTrace();
        }
    }

    public boolean loadCredentials(String username, String password, String userType) {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials.length == 3) {
                    String UserName = credentials[0].trim();
                    String Password = credentials[1].trim();
                    String UserType = credentials[2].trim();

                    if (UserName.equals(username) && Password.equals(password) && UserType.equals(userType)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            showAlert("File Not Found", "The credentials file was not found.");
            e.printStackTrace();
        } catch (IOException e){
            showAlert("File Error", "Error reading credentials from file.");
            e.printStackTrace();
        }catch (Exception e){
            showAlert("Unknown Error", "An unknown error occurred." + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(String title, String message){
        System.out.println(title + ": " + message);
    }
}
