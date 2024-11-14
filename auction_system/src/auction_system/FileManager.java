package auction_system;

import javafx.scene.control.TextArea;

import java.io.*;
import java.util.List;



public class FileManager {

    private CategoryController categoryController;
    private CommissionController commissionController;
    private PremiumController premiumController;

    public void saveRegisteredUserData(TextArea itemListArea) {
        try (FileWriter writer = new FileWriter("registered_user_data.txt", true)) {
            writer.write(itemListArea.getText());
            showAlert("Data Saved Successfully", "Data saved successfully to registered_user_data.txt");
        } catch (Exception e) {
            showAlert("File Error", "Error saving data to file.");
            e.printStackTrace();
        }
    }

    public void loadRegisteredUserData(TextArea itemListArea) {
        File file = new File("registered_user_data.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader("registered_user_data.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    itemListArea.appendText(line + "\n");
                }
            } catch (IOException e) {
                showAlert("File Error", "Error loading data from file.");
                e.printStackTrace();
            }
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
        } catch (IOException e) {
            showAlert("File Error", "Error loading credentials from file.");
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(String title, String message){
        System.out.println(title + ": " + message);
    }
}
