public class BankAccount{
    //balance, name, age, address, account no
    private int customerBalance;
    private String customerName;
    private int customerAge;
    private String customerAddress;
    private int customerAccNo;

    public BankAccount(String name, int age, String addr){
        this.customerName = name;
        this.customerAge = age;
        this.customerAddress = addr;
        // this.customerAccNo = accNo ;
        this.customerBalance = 0;
    }

    public BankAccount(String name, int age, String addr, int accNo){
        this(name, age, addr);
        this.customerAccNo = accNo;
    }

    //ExceedAmountAxception
    public static class ExceedAmountException extends Exception{
        public ExceedAmountException(String message){
            super(message);
        }
    }

    // public BankAccount(String accNo){
    //     this.customerAccNo = accNo;
    // }

    //functions-deposit and withdraw
    public void depositMoney(int depositAmount){
        customerBalance += depositAmount;
        // return "Account No: " + customerAccNo + " Balance: " + customerBalance;
    }

    public void withdrawMoney(int withdrawAmount) throws ExceedAmountException{
        if(customerBalance - withdrawAmount >0){
            customerBalance -= withdrawAmount;
        }
        else{
            System.out.println("Insufficient Balance.");
            throw new ExceedAmountException("Insufficient Balance to withdraw");
        }
        // return "Account No: " + customerAccNo + " Balance: " + customerBalance;
    }

    public void setBalance(int balance){
        this.customerBalance = balance;
    }

    public int getBalance(){
        return this.customerBalance;
    }

    public String getName(){
        return this.customerName;
    }

    public String getAge(){
        return ""+customerAge;
    }

    public int getAccNo(){
        return customerAccNo;
    }

    public String getAddress(){
        return customerAddress;
    }
    
    public String toString(){
        return "Acc. No.: " + customerAccNo + " Name: " + customerName + " Age: " + customerAge + " Address: " + customerAddress + " Balance: " + customerBalance;
    }
}