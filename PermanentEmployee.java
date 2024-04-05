public class PermanentEmployee extends Employee implements RaiseSalary { 
    public double raise;

    PermanentEmployee(String name, double salary) {
        super(name, salary);
    }

    @Override
    public double calculateSalary() {
        return this.salary + this.raise;
    }

    @Override
    public void askRaise(double raise) {
        this.raise = raise;
    }

    @Override
    public String toString() {
        return "[" + this.employeeId + "] " + this.name + " | Salary : " + String.format("%.0f", this.calculateSalary()) + " | Kenaikan : " + String.format("%.0f", this.raise);
    }
}