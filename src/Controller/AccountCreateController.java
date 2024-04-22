package Controller;

import View.AccountCreateView;
import Model.AccountCreateModel;
import javax.swing.JOptionPane;

public class AccountCreateController {

    private final AccountCreateView accountCreate;

    public AccountCreateController(AccountCreateView accountCreate) {
        this.accountCreate = accountCreate;
    }

    public void AccountCreateController() {
        this.accountCreate.setController(this);
    }

    public void handleAccountCreation() {

        AccountCreateView accountcreate = new AccountCreateView();
        AccountCreateController accountcontroller = new AccountCreateController(accountcreate);
        accountcreate.setController(accountcontroller);

        //get variables username, password and acctype
        String username = accountCreate.getUsername();
        char[] password = accountCreate.getPassword();
        String acctype = accountCreate.getAccType();

        String pass = new String(password);
        String errorMessage = validatePassword(pass);
        if (!errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(null, errorMessage);
        } else {
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(accountCreate, "User Name Field is Empty!\nPlease Add a User Name");
            } else if (password.length == 0) {
                JOptionPane.showMessageDialog(accountCreate, "Password Field is Empty!");
            } else if (acctype.equals("Select Account Type")) {
                JOptionPane.showMessageDialog(accountCreate, "Please Select The Account Type!");
            } else {
                try {
                    if (AccountCreateModel.createAccount(username, new String(password), acctype)) {
                        JOptionPane.showMessageDialog(accountCreate, "Account Created Successfully!\nAccount Created For a :"+acctype+" !");
                    } else {
                        //JOptionPane.showMessageDialog(accountCreate, "Error! Try Again!");
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(accountCreate, "An Error Occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    //validation for password
    private String validatePassword(String password) {

        //set all variable counts to zero
        int upperCount = 0;
        int lowerCount = 0;
        int digitCount = 0;
        int symbolCount = 0;

        //calculate and count characters in password
        for (char chr : password.toCharArray()) {
            if (Character.isUpperCase(chr)) {
                upperCount++;
            } else if (Character.isLowerCase(chr)) {
                lowerCount++;
            } else if (Character.isDigit(chr)) {
                digitCount++;
            } else {
                symbolCount++;
            }
        }
        
        //check if password have requirements -> all errors are sent as a one error message
        StringBuilder errorMessage = new StringBuilder();

        if (upperCount < 2) {
            errorMessage.append("Password Must Include Two Uppercase Letters!\n");
        }
        if (lowerCount < 2) {
            errorMessage.append("Password Must Include Two Lowercase Letters!\n");
        }
        if (digitCount < 1) {
            errorMessage.append("Password Must Include A Number!\n");
        }
        if (symbolCount < 1) {
            errorMessage.append("Password Must Include A Symbol!\n");
        }
        return errorMessage.toString();
    }
}
