import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-input-field',
  imports: [ReactiveFormsModule],
  templateUrl: './input-field.html',
  styleUrl: './input-field.css',
})
export class InputField {
  private readonly _id = `field-${crypto.randomUUID()}`;
  title = input.required<string>();
  id = input(this._id);
  placeholder = input("");
  control = input.required<FormControl>();
}
