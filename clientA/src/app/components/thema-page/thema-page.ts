import { HttpClient, HttpClientModule } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import {
  AbstractControl,
  ControlEvent,
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ThemaModel } from '../../Models/thema.model';
import { ThemaService } from '../../services/thema.service';
import { RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-thema-page',
  standalone: true,
  imports: [FormsModule, RouterLink, ReactiveFormsModule],
  templateUrl: './thema-page.html',
  styleUrl: './thema-page.css',
})
export class ThemaPage implements OnInit {
  themaService = inject(ThemaService);
  themas = signal<ThemaModel[]>([]);
  //thema = new ThemaModel();
  readonly form = inject(FormBuilder).nonNullable.group({
    id: [0],
    name: ['', [Validators.required, Validators.maxLength(50)]],
  });
  ngOnInit(): void {
    this.themaService.getAll().subscribe((res) => {
      this.themas.set(res);
    });
  }
  //constructor(public client: HttpClient) {}

  pressButton() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const thema = {
      ...this.form.getRawValue(),
    } as ThemaModel;
    this.themaService.create(thema).subscribe((res) => {
      this.themas.update((list) => [...list, res]);
      this.form.reset();
    });
  }
  pressDelete() {
    const id = this.form.controls.id.value;
    this.themaService
      .delete(id)
      .subscribe(() =>
        this.themas.update((l) => l.filter((t) => t.id !== id)),
      );
    this.cancelEdit();
  }
  startEdit(theme: ThemaModel) {
    this.form.patchValue(theme);
  }
  cancelEdit() {
    this.form.reset();
  }
  saveEdit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const thema = {
      ...this.form.getRawValue(),
    } as ThemaModel;
    this.themaService.update(thema).subscribe((res) => {
      this.themas.update((l) => {
        const index = l.findIndex((t) => t.id === thema.id);
        l[index] = res;
        return l;
      });
      this.cancelEdit();
    });
  }
}
