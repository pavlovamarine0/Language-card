import { Component, inject, OnInit, signal } from '@angular/core';
import { SentenceService } from '../../../services/sentence.service';
import { SentenceModel } from '../../../Models/sentence.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputField } from '../../../core/components/forms/input-field/input-field';

@Component({
  selector: 'app-select-sentences-modal',
  imports: [ InputField, ReactiveFormsModule],
  templateUrl: './select-sentences-modal.html',
  styleUrl: './select-sentences-modal.css',
})
export class SelectSentencesModal implements OnInit {
  sentenceService = inject(SentenceService);
  allSentences = signal<SentenceModel[]>([]); //allSentencesByCardId
  offeredSentences = signal<SentenceModel[]>([]); // offeredKeyWordSentences to add
  modal = inject(NgbActiveModal);
  keyWord = '';
  cardId = 0;
  readonly form = inject(FormBuilder).nonNullable.group({
    id: [0],
    sentence: ['', [Validators.required, Validators.maxLength(150)]],
    translate: ['', [Validators.required, Validators.maxLength(150)]],
  });
  ngOnInit(): void {
    if (!this.cardId) {
      return;
    }
    this.sentenceService.getAllByCardId(Number(this.cardId)).subscribe((s) => {
      this.allSentences.set(s);
    });
    this.getKeyWordSentences();
    console.log(this.allSentences);
  }
  isSentenceSelected(sentence: SentenceModel): boolean {
    return this.allSentences().some((s) => s.id === sentence.id);
  }
  chooseSentence(selectedSentence: SentenceModel) {
    
      this.allSentences.update((sentences) => {
        if (sentences.some((s) => s.id === selectedSentence.id)) {
          return sentences;
        }
        return [...sentences, selectedSentence];
      });
      this.offeredSentences.update((sentences) =>{
        return sentences.filter((s) => s.id !== selectedSentence.id);
      })
  }
  /*filter(array: SentenceModel[]){
    const list:SentenceModel[] =[];
    const selectedSentence = new SentenceModel();
    for(var i of array){
      if(i.id!=selectedSentence.id){
        list.push(i);
      }
    }
    return list;
  }*/

  saveBtn() {
    const newExample = this.form.getRawValue() as SentenceModel;
    this.sentenceService.create(newExample).subscribe({
      next: (res) => {
        this.form.patchValue(res);
        this.form.markAsDirty();
        this.allSentences.update((s) => [...s, res]);
        this.form.reset();
        this.modal.close(this.allSentences());
      },
    });
  }
  getKeyWordSentences(){
    if(!this.keyWord){
      return;
    }
    this.sentenceService.getAllByKeyWord(this.keyWord).subscribe((s) => {
      this.offeredSentences.set(s);
    });
  }
  deleteBtn(sentence: SentenceModel){
    this.sentenceService.unbindSentence(this.cardId, sentence.id).subscribe(()=> 
    this.allSentences.update((sentences)=>
    sentences.filter((s)=> s.id !== sentence.id))
  );
  }
}
