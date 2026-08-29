import { HttpClient, HttpClientModule } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ThemaModel } from '../../Models/thema.model';

@Component({
  selector: 'app-thema-page',
  imports: [FormsModule],
  templateUrl: './thema-page.html',
  styleUrl: './thema-page.css',
})
export class ThemaPage implements OnInit {
  private readonly url = 'http://localhost:8080/api/themas';
  themas = signal<ThemaModel[]>([]);
  private readonly _client = inject(HttpClient);
  thema = new ThemaModel();
  ngOnInit(): void {
    this._client.get<ThemaModel[]>(this.url).subscribe((res) => {
      this.themas.set(res);
    });
  }

  //constructor(public client: HttpClient) {}

  pressButton() {
    if (!this.thema.name.trim()) {
      console.log('Not working');
      return;
    }
    this._client.post<ThemaModel>(this.url, { name: this.thema.name }).subscribe((res) => {
      this.themas.update((list) => [...list, res]);
      this.thema = new ThemaModel();
    });
  }
  pressDelete() {
    this._client
      .delete<void>(`${this.url}/${this.thema.id}`)
      .subscribe(() => this.themas.update((l) => l.filter((t) => t.id !== this.thema.id)));
    this.canselEdit();
  }
  startEdit(theme: ThemaModel) {
    this.thema = { ...theme };
  }
  canselEdit() {
    this.thema = new ThemaModel();
  }
  saveEdit() {
    if (!this.thema.name.trim()) {
      return;
    }
    this._client.put<ThemaModel>(this.url, this.thema).subscribe((res) => {
      this.themas.update((l) => {
        const index = l.findIndex((t) => t.id === this.thema.id);
        l[index] = res;
        return l;
      });
      this.canselEdit();
    });
  }
}
