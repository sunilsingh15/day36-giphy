import { Component, Inject, OnInit, inject } from '@angular/core';
import { Form, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { GifsearchService } from './gifsearch.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'client';

  searchForm!: FormGroup;
  builder = inject(FormBuilder);
  service = inject(GifsearchService);
  gifList: string[] = [];
  sub$!: Subscription;

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.searchForm = this.builder.group({
      search: new FormControl('', [Validators.required, Validators.minLength(2)])
    });
  }

  search() {
    this.gifList = [];
    let query = this.searchForm.value['search'];

    this.sub$ = this.service.getGifs(query).subscribe({
                  next: (result) => {
                    for (let index = 0; index < result.length; index++) {
                      const element = result[index];
                      this.gifList.push(element);
                    }
                  },
                  error: (err) => { console.error(err); },
                  complete: () => { this.sub$.unsubscribe(); }
    })
  }


}
