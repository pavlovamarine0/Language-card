import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { CardModel } from '../Models/card.model';
import { environment } from '../../environment/environment';

@Service()
export class CardService {
  private readonly url = environment.apiUrl + 'cards';
  private readonly _client = inject(HttpClient);
  getAll(themaId: number) {
    const params = new HttpParams().append('themaId', themaId);
    return this._client.get<CardModel[]>(this.url, { params });
  }

  create(card: CardModel) {
    return this._client.post<CardModel>(this.url, card);
  }
  delete(id: number) {
    return this._client.delete<void>(`${this.url}/${id}`);
  }
  update(card: CardModel) {
    return this._client.put<CardModel>(this.url, card); //`${this.url}/${card.id}`
  }
  getById(id: number) {
    return this._client.get<CardModel>(`${this.url}/${id}`);
  }
  getWithoutIdsThema(){
    return this._client.get<CardModel[]>(this.url + '/without-theme');
  }
}
