import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UploadFile } from '../models/uploadFile';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit{

  form!:FormGroup
  uploadFile!:UploadFile

  constructor(private fb:FormBuilder){ }

  ngOnInit(): void {
      this.form = this.createForm()
  }

  createForm():FormGroup {
    return this.fb.group({
      name:this.fb.control('',Validators.required),
      title:this.fb.control('',Validators.required),
      comments:this.fb.control(''),
      archive:this.fb.control('',Validators.required),
    })
  }

  processForm() {
    this.uploadFile = this.form.value
    console.log(this.uploadFile)
  }

}
