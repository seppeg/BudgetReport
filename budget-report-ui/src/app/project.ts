import {Workorder} from "./workorder";

export class Project {
    description: string;
    workorder: Workorder[];
    budget: number;
    hoursSpent: number;
}