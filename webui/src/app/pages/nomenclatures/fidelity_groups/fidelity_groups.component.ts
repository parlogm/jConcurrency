import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {FidelityGroups} from "../../../model/fidelity_groups.model";
import {MatPaginator, MatTableDataSource} from "@angular/material";

@Component({
    selector: 's-fidelity_groups-pg',
    templateUrl: './fidelity_groups.component.html',
    styleUrls: ['./fidelity_groups.scss'],
})

export class FidelityGroupsComponent implements OnInit {

    displayedColumns: string[] = ['id', 'groupName'];
    dataSource = new MatTableDataSource<FidelityGroups>(ELEMENT_DATA);

    @ViewChild(MatPaginator) paginator: MatPaginator;

    ngOnInit() {
        this.dataSource.paginator = this.paginator;
    }

}

const ELEMENT_DATA: FidelityGroups[] = [
    {id: 1, groupName: 'Hydrogen'},
    {id: 2, groupName: 'Helium'},
    {id: 3, groupName: 'Lithium'},
    {id: 4, groupName: 'Beryllium'},
    {id: 5, groupName: 'Boron'},
    {id: 6, groupName: 'Carbon'},
    {id: 7, groupName: 'Nitrogen'},
    {id: 8, groupName: 'Oxygen'},
    {id: 9, groupName: 'Fluorine'},
    {id: 10, groupName: 'Neon'},
    {id: 11, groupName: 'Sodium'},
    {id: 12, groupName: 'Magnesium'},
    {id: 13, groupName: 'Aluminum'},
    {id: 14, groupName: 'Silicon'},
    {id: 15, groupName: 'Phosphorus'},
    {id: 16, groupName: 'Sulfur'},
];