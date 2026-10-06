import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CardService } from '../../services/card.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CardModel } from '../../Models/card.model';
import {
  AbstractControl,
  ControlEvent,
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ThemaService } from '../../services/thema.service';
import { ThemaModel } from '../../Models/thema.model';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SelectThemasModal } from '../thema-page/select-themas-modal/select-themas-modal';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-new-card-page',
  imports: [FormsModule, RouterLink, ReactiveFormsModule],
  templateUrl: './new-card-page.html',
  styleUrl: './new-card-page.css',
})
export class NewCardPage implements OnInit {
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (!id) {
        return;
      }
      this.cardService.getById(Number(id)).subscribe({
        next: (card) => {
          this.form.patchValue(card);

          this.themaService.getAll().subscribe({
            next: (themas) => {
              const selected = themas.filter((t) => card.themaIds.includes(t.id));

              this.form.controls.themas.setValue(selected);
            },
            error: (err) => {
              console.error(err);
            },
          });
        },
        error: (err) => {
          console.log(id);
        },
      });
    });
  }

  private cardService = inject(CardService);
  private themaService = inject(ThemaService);
  private toastr = inject(ToastrService);
  private modalService = inject(NgbModal);
  // card = signal<CardModel>(new CardModel());
  //selectedThemas = signal<ThemaModel[]>([]);
  private route = inject(ActivatedRoute);
  readonly form = inject(FormBuilder).nonNullable.group({
    id: [0],
    word: ['', [Validators.required, Validators.maxLength(50)]],
    transWord: ['', [Validators.required, Validators.maxLength(50)]],
    plural: ['', [Validators.maxLength(50)]],
    themas: [[] as ThemaModel[], [Validators.required, this.hasTheme]],
  });
  public readonly selectedThemas = toSignal(this.form.controls.themas.valueChanges, {
    initialValue: this.form.controls.themas.value,
  });

  //constructor(public client: HttpClient) {}
  hasTheme(control: AbstractControl) {
    const value = control.value as ThemaModel[];
    if (!value) {
      return null;
    }
    return value.length < 1
      ? {
          HasNoThemas: true,
        }
      : null;
  }

  createButton() {
    const card = {
      ...this.form.getRawValue(),
      themaIds: this.selectedThemas().map((t) => t.id),
      themas: undefined,
    } as CardModel;
    if (this.isEditMode()) {
      this.updateCard();
      return;
    }
    this.cardService.create(card).subscribe({
      next: (res) => {
        this.form.patchValue({ ...res, themas: this.selectedThemas() });
        this.toastr.success('The card is created!');
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
  updateCard() {
    const card = {
      ...this.form.getRawValue(),
      themaIds: this.selectedThemas().map((t) => t.id),
      themas: undefined,
    } as CardModel;
    this.cardService.update(card).subscribe({
      next: (res) => {
        this.form.patchValue({ ...res, themas: this.selectedThemas() });
        this.toastr.success('The card is updated!');
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
  pressDelete() {
    this.cardService.delete(this.form.controls.id.value).subscribe(() => {});
  }
  themasBtn() {
    const modal = this.modalService.open(SelectThemasModal);
    modal.componentInstance.selectedThemas = [...this.selectedThemas()];
    modal.result.then((data) => {
      this.form.controls.themas.setValue(data);
      this.form.controls.themas.markAsDirty();
      this.form.controls.themas.markAsTouched();
      console.log(data);
    });
  }
  isEditMode() {
    return !!this.form.controls.id.value;
  }
}
