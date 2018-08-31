export class Project {
    private name: string;
    private budget: number;
    private hoursSpent: number;


    constructor(name: string, budget: number, hoursSpent: number) {
        this.name = name;
        this.budget = budget;
        this.hoursSpent = hoursSpent;
    }
}
