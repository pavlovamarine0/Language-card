import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { SentenceModel } from '../Models/sentence.model';
import { environment } from '../../environment/environment';

@Service()
export class SentenceService {
  private readonly url = environment.apiUrl + 'sentences';
  private readonly _client = inject(HttpClient);
  getAllByCardId(cardId: number) {
    const params = new HttpParams().append('cardId', cardId);
    return this._client.get<SentenceModel[]>(this.url, { params });
  }
  getAll() {
    return this._client.get<SentenceModel[]>(this.url);
  }
  getAllByKeyWord(keyWord: string) {
  return this._client.get<SentenceModel[]>(
    `${this.url}?keyWord=${encodeURIComponent(keyWord)}`
  );
}

  create(sentence: SentenceModel) {
    
    return this._client.post<SentenceModel>(this.url, sentence);
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
