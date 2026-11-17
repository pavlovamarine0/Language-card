import { Routes } from '@angular/router';
import { ThemaPage } from './components/thema-page/thema-page';
import { CardPage } from './components/card-page/card-page';
import { Counter } from './counter/counter';
import { NewCardPage } from './components/new-card-page/new-card-page';
import { UserPage } from './components/user-page/user-page';
import { LoginPage } from './components/login-page/login-page';

export const routes: Routes = [
    {
        path: '',
        redirectTo: "thema",
        pathMatch: "full"
    },
    {
        path: 'thema',
        component: ThemaPage
    },
    {
        path: 'cards',
        component: CardPage
    },
    {
        path: 'card',
        component: NewCardPage
    },
    {
        path: 'card/:id',
        component: NewCardPage
    },
    {
        path: 'user',
        component: UserPage
    },
     {
        path: 'login',
        component: LoginPage
    },
    {
        path: "**",
        component: Counter
    }
];
