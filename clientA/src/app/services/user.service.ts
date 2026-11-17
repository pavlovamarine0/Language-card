import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { UserModel } from '../Models/user.model';
import { environment } from '../../environment/environment';
@Service()
export class UserService {
  private readonly url = environment.apiUrl + 'users';
  private readonly _client = inject(HttpClient);

  getAll() {
    return this._client.get<UserModel[]>(this.url);
  }
  create(user: UserModel) {
    return this._client.post<UserModel>(this.url, {
      userName: user.userName,
      email: user.email,
      password: user.password,
    });
  }
  login(user: UserModel) {
    return this._client.post<UserModel>(this.url + '/login', {
      email: user.email,
      password: user.password,
    });
  }
  getMe(userId: number) {
    return this._client.get<UserModel>(`${this.url}/me`, {
      headers: {
        Auth: userId.toString(),
      },
    });
  }
  update(user: UserModel) {
    return this._client.put<UserModel>(this.url, {
      userName: user.userName,
      email: user.email,
      password: user.password,
    });
  }
  delete(id: number) {
    return this._client.delete<void>(`${this.url}/${id}`);
  }
}
