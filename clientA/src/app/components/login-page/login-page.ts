import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UserModel } from '../../Models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage implements OnInit {
  userLogin = new UserModel();
  userService = inject(UserService);
  router = inject(Router);
  ngOnInit(): void {
    if (localStorage.getItem('user')) {
      this.router.navigateByUrl('thema');
    }
  }

  login() {
    this.userService.login(this.userLogin).subscribe((u) => {
      localStorage.setItem('user', JSON.stringify(u));
      this.router.navigateByUrl('thema');
    });
  }
}
