public class ContractEmployee extends Employee implements RaiseSalary, ExtendContractDuration {
    public int contractDuration;
    public double raise;

    ContractEmployee(String name, double salary, int contractDuration) {
        super(name, salary);
        this.contractDuration = contractDuration;
    }

    @Override
    public void askRaise(double raise) {
        this.raise = raise;
    }

    @Override
    public double calculateSalary() {
        return (this.salary + this.raise) * this.getSalaryMultiplier();
    }

    @Override
    public void extendContract(int duration) {
        this.contractDuration = this.contractDuration + duration;
    }

    @Override
    public String toString() {
        return "[" + this.employeeId + "] " + this.name + " | Salary : " + String.format("%.0f", this.calculateSalary()) + " | Kenaikan : " + String.format("%.0f", this.raise) + " | Kontrak : " + this.contractDuration;
    }

    private double getSalaryMultiplier() {
        if (this.contractDuration <= 6) {
            return 1.0;
        } else if (this.contractDuration <= 12) {
            return 1.5;
        } else if (this.contractDuration > 12) {
            return 2.0;
        } else {
            return 1.0;
        }
    }
}
