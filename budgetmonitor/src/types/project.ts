import {Workorder} from './workorder';
import {Budget} from './budget';

export class Project {
    private description: string;
    private workorder: Workorder[];
    private budgets: Budget[];
    private hoursSpent: number;


    constructor(description: string, workorder: Workorder[], budgets: Budget[], hoursSpent: number) {
        this.description = description;
        this.workorder = workorder;
        this.budgets = budgets;
        this.hoursSpent = hoursSpent;
    }
}
