import { Component, inject, OnInit, signal } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserModel } from '../../Models/user.model';

@Component({
  selector: 'app-user-page',
  imports: [],
  templateUrl: './user-page.html',
  styleUrl: './user-page.css',
})
export class UserPage implements OnInit {
  ngOnInit(): void {
    const userId = Number(localStorage.getItem('userId'));
    this.userService.getMe(userId).subscribe((user) => {
      this.user = user;
    });
  }
  private userService = inject(UserService);
  private userName = signal('');
  private email = signal('');
  private user = new UserModel();
}
