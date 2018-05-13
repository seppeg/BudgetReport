import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './app.guard';
import { TestComponent } from './test/test.component';

const appRoutes: Routes = [
    { path: '', component: TestComponent },
];

export const routing = RouterModule.forRoot(appRoutes);