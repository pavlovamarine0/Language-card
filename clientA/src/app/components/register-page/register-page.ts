import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UserModel } from '../../Models/user.model';

@Component({
  selector: 'app-register-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css',
})
export class RegisterPage {
  userRegister = new UserModel;
  userService = inject(UserService);

  register(){
    this.userService.create(this.userRegister).subscribe((u)=>
    console.log(u)
    )
  }
}
