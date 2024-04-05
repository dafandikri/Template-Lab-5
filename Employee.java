abstract class Employee {
    public int employeeId;
    public static int employeeCnt = 0;
    public String name;
    public double salary;

    public Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
        this.employeeId = employeeCnt;
        employeeCnt++;
    
    }
    public abstract double calculateSalary();
    public abstract String toString();
}
