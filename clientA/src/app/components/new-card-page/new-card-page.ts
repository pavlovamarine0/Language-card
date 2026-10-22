import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CardService } from '../../services/card.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CardModel } from '../../Models/card.model';
import { forkJoin, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
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
import { InputField } from '../../core/components/forms/input-field/input-field';
import { SentenceService } from '../../services/sentence.service';
import { SentenceModel } from '../../Models/sentence.model';
import { SelectSentencesModal } from '../sentences/select-sentences-modal/select-sentences-modal';

@Component({
  selector: 'app-new-card-page',
  imports: [FormsModule, RouterLink, ReactiveFormsModule, InputField],
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
          this.sentenceService.getAllByCardId(Number(id)).subscribe({
            next: (sentences) => {
              const saved = sentences.filter((s) => card.sentenceIds.includes(s.id));
              this.form.controls.sentences.setValue(saved);
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
  private sentenceService = inject(SentenceService);
  private toastr = inject(ToastrService);
  private modalService = inject(NgbModal);
  private route = inject(ActivatedRoute);
  readonly form = inject(FormBuilder).nonNullable.group({
    id: [0],
    word: ['', [Validators.required, Validators.maxLength(50)]],
    translation: ['', [Validators.required, Validators.maxLength(50)]],
    plural: ['', [Validators.maxLength(50)]],
    themas: [[] as ThemaModel[], [Validators.required, this.hasTheme]],
    sentences: [[] as SentenceModel[]],
  });
  public readonly selectedThemas = toSignal(this.form.controls.themas.valueChanges, {
    initialValue: this.form.controls.themas.value,
  });
  public readonly selectedSentences = toSignal(this.form.controls.sentences.valueChanges, {
    initialValue: this.form.controls.sentences.value,
  });
  newSentence = signal('');
  newTranslate = signal('');
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
  addSentence() {
    const sentence = this.newSentence().trim();
    const translate = this.newTranslate().trim();
    if (!sentence || !translate) {
      return;
    }
    const newExample = new SentenceModel();
    newExample.sentence = sentence;
    newExample.translation = translate;
    this.form.controls.sentences.setValue([...this.form.controls.sentences.value, newExample]);
    this.form.controls.sentences.markAsDirty();
    this.newSentence.set('');
    this.newTranslate.set('');
  }
  createButton() {
    const card = {
      ...this.form.getRawValue(),
      themaIds: this.selectedThemas().map((t) => t.id),
      sentenceIds: this.selectedSentences().map((s) => s.id),
      themas: undefined,
      sentences: [],
    } as CardModel;

    console.log(card);
    if (this.isEditMode()) {
      this.updateCard();
      return;
    }
    this.cardService.create(card).subscribe({
      next: (res) => {
        this.form.patchValue({
          ...res,
          themas: this.selectedThemas(),
          sentences: res.sentences ?? [],
        });
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
      sentenceIds: this.selectedSentences().map((s) => s.id),
      sentences: undefined,
    } as CardModel;
    this.cardService.update(card).subscribe({
      next: (res) => {
        this.form.patchValue({
          ...res,
          themas: this.selectedThemas(),
          sentences: this.selectedSentences(),
        });
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
  sentencesBtn() {
    const modal = this.modalService.open(SelectSentencesModal);
    modal.componentInstance.keyWord = this.form.controls.word.value;
    modal.componentInstance.cardId = this.form.controls.id.value;
    modal.result.then((data) => {
      this.form.controls.sentences.setValue(data);
      this.form.controls.sentences.markAsDirty();
      this.form.controls.sentences.markAsTouched();
      console.log(data);
    });
  }
  isEditMode() {
    return !!this.form.controls.id.value;
  }
}
