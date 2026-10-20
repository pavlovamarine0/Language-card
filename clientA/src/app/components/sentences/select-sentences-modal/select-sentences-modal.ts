import { Component, inject, signal } from '@angular/core';
import { SentenceService } from '../../../services/sentence.service';
import { SentenceModel } from '../../../Models/sentence.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-select-sentences-modal',
  imports: [],
  templateUrl: './select-sentences-modal.html',
  styleUrl: './select-sentences-modal.css',
})
export class SelectSentencesModal implements OnInit{

  sentenceService = inject(SentenceService);
  allSentences = signal<SentenceModel[]>([]);
  selectedSentences : SentenceModel[] = [];
  modal = inject(NgbActiveModal);
   ngOnInit(): void {
    this.sentenceService.getAllByCardId().subscribe(themas =>{
      this.themas.set(themas);
    });
    console.log(this.selectedThemas);
  }

}
