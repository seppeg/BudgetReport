import {InMemoryDbService} from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        const projects = [
            {
                description: "Java Guild",
                workorder: "COCFL874.001",
                budget: 150,
                hoursSpent: 70
            },
            {
                description: "Architect Guild",
                workorder: "COCFL874.002",
                budget: 200,
                hoursSpent: 89
            }
        ];
        return {projects};
    }
}