import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { SentenceModel } from '../Models/sentence.model';
import { environment } from '../../environment/environment';

@Service()
export class SentenceService {
  private readonly url = environment.apiUrl + 'sentence';
  private readonly _client = inject(HttpClient);
  getAll(cardId: number) {
    const params = new HttpParams().append('cardId', cardId);
    return this._client.get<SentenceModel[]>(this.url, { params });
  }

  create(sentence: SentenceModel, cardId: number) {
     const params = new HttpParams().set('cardId', cardId);
    return this._client.post<SentenceModel>(this.url, sentence, {params});
  }
  delete(id: number) {
    return this._client.delete<void>(`${this.url}/${id}`);
  }
  update(sentence: SentenceModel) {
    return this._client.put<SentenceModel>(this.url, sentence); //`${this.url}/${card.id}`
  }
  getById(id: number) {
    return this._client.get<SentenceModel>(`${this.url}/${id}`);
  }
  getWithoutIdsCard(){
    return this._client.get<SentenceModel[]>(this.url + '/without-card');
  }
}
