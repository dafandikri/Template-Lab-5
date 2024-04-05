public class InternEmployee extends Employee implements ExtendContractDuration {
    public int contractDuration;
    public double raise;

    InternEmployee(String name, double salary, int contractDuration) {
        super(name, salary);
        this.contractDuration = contractDuration;
    }

    @Override
    public double calculateSalary() {
        return this.salary * getSalaryMultiplier();
    }

    @Override
    public void extendContract(int duration) {
        this.contractDuration += duration;
    }

    @Override
    public String toString() {
        return this.employeeId + " " + this.name + " | Salary : " + this.calculateSalary() + " | Kenaikan : " + this.raise;
    }

    private double getSalaryMultiplier() {
        if (this.contractDuration <= 6) {
            return 1.0;
        } else if (this.contractDuration <= 12) {
            return 1.25;
        } else if (this.contractDuration > 12) {
            return 1.5;
        } else {
            return 1.0;
        }
    }
}
