import {Workorder} from "./workorder";
import {Budget} from "./budget";

export class Project {
    description: string;
    workorder: Workorder[];
    budgets: Budget[];
    hoursSpent: number;
}