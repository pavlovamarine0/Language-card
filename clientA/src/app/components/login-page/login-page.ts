import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UserModel } from '../../Models/user.model';

@Component({
  selector: 'app-login-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  userLogin = new UserModel;
  userService = inject(UserService);

  login(){
    this.userService.login(this.userLogin).subscribe((u)=>
    console.log(u)
    )
  }

}
