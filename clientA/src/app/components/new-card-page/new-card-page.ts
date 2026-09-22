import { Component, inject, OnInit, signal } from '@angular/core';
import { CardService } from '../../services/card.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CardModel } from '../../Models/card.model';
import { FormsModule } from '@angular/forms';
import { ThemaService } from '../../services/thema.service';
import { ThemaModel } from '../../Models/thema.model';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SelectThemasModal } from '../thema-page/select-themas-modal/select-themas-modal';

@Component({
  selector: 'app-new-card-page',
  imports: [FormsModule, RouterLink],
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
          this.card.set(card);

          this.themaService.getAll().subscribe({
            next: (themas) => {
              const selected = themas.filter((t) => card.themaIds.includes(t.id));

              this.selectedThemas.set(selected);
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
  toastr = inject(ToastrService);
  private modalService = inject(NgbModal);
  card = signal<CardModel>(new CardModel());
  selectedThemas = signal<ThemaModel[]>([]);
  route = inject(ActivatedRoute);

  //constructor(public client: HttpClient) {}

  createButton() {
    if (!this.card().word.trim()) {
      console.log('Not working');
      return;
    }
    this.card().themaIds = this.selectedThemas().map((t) => t.id);
    if (this.isEditMode()) {
      this.cardService.update(this.card()).subscribe({
        next: (res) => {
          this.card.set(res);
          this.toastr.success('The card is updated!');
        },
        error: (err) => {
          console.error(err);
        },
      });
      return;
    }
    this.cardService.create(this.card()).subscribe({
      next: (res) => {
        this.card.set(res);
        this.toastr.success('The card is created!');
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
  pressDelete() {
    this.cardService.delete(this.card().id).subscribe(() => {});
  }
  themasBtn() {
    const modal = this.modalService.open(SelectThemasModal);
    modal.componentInstance.selectedThemas = this.selectedThemas();
    modal.result.then((data) => {
      this.selectedThemas.set(data);
      console.log(data);
    });
  }
  isEditMode() {
    return this.card().id !== 0;
  }
}
