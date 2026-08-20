import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Counter } from "./counter/counter";
import { ThemaPage } from './components/thema-page/thema-page';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Counter, ThemaPage],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

}
