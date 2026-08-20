import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ThemaModel } from '../../Models/thema.model';

@Component({
  selector: 'app-thema-page',
  imports: [FormsModule],
  templateUrl: './thema-page.html',
  styleUrl: './thema-page.css',
})
export class ThemaPage implements OnInit{
  thema = '';
  private readonly url = "http://localhost:8080/api/themas";
  themas: ThemaModel[] = [];
  private readonly _client = inject(HttpClient);
  ngOnInit(): void {
    this._client.get<ThemaModel[]>(this.url).subscribe(res => this.themas = res);
    
  }
  //constructor(public client: HttpClient) {}

  pressButton() {
    if(!this.thema.trim()){
      console.log("Not working");
      return;
    }
    this._client.post<ThemaModel>(this.url, {name: this.thema})
      .subscribe(res => {this.themas.push(res);
       this.thema = '';
      })
  }
}
