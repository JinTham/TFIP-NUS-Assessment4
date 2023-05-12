import { Component, OnDestroy, OnInit } from '@angular/core';
import { UploadFileService } from '../services/upload-file.service';
import { Bundle } from '../models/bundle';
import { Subscription } from 'rxjs/internal/Subscription';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit, OnDestroy {

  bundleId!:string
  bundle!:Bundle
  sub$!:Subscription

  constructor(private uploadFileSvc:UploadFileService, private actRoute:ActivatedRoute) { }

  async ngOnInit() {this.sub$ = this.actRoute.params.subscribe(
    async (params) => {
      this.bundleId = params["bundleId"]
      console.log(this.bundleId)
      this.bundle = await this.uploadFileSvc.getBundle(this.bundleId)
    }) 
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }

}
