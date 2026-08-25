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
  thema = '';
  private readonly url = 'http://localhost:8080/api/themas';
  themas = signal<ThemaModel[]>([]);
  private readonly _client = inject(HttpClient);
  // private readonly _cdr = inject(ChangeDetectorRef);
  editingId: number | null = null;
  editingName: string = '';
  ngOnInit(): void {
    this._client.get<ThemaModel[]>(this.url).subscribe((res) => {
      this.themas.set(res);
      //this._cdr.markForCheck();
    });
  }

  //constructor(public client: HttpClient) {}

  pressButton() {
    if (!this.thema.trim()) {
      console.log('Not working');
      return;
    }
    this._client.post<ThemaModel>(this.url, { name: this.thema }).subscribe((res) => {
      this.themas.update((list) => [...list, res]);
      this.thema = '';
    });
  }
  pressDelete(id: number) {
    this._client
      .delete<void>(`${this.url}/${id}`)
      .subscribe(() => this.themas.update((l) => l.filter((t) => t.id !== id)));
  }
  startEdit(theme: ThemaModel) {
    this.editingId = theme.id;
    this.editingName = theme.name;
  }
  canselEdit() {
    this.editingId = null;
    this.editingName = '';
  }
  saveEdit(theme: ThemaModel) {
    if (!this.editingName.trim()) {
      return;
    }
    const updateTheme = { ...theme, name: this.editingName };
    this._client.put<ThemaModel>(this.url, updateTheme).subscribe((res) => {
      this.themas.update((l) => {
        const index = l.findIndex(t => t.id === theme.id)
        l[index] = res;
        return l;
      })
      this.canselEdit();
    });
  }
}
