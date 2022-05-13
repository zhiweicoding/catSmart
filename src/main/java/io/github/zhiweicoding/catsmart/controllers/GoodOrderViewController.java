package io.github.zhiweicoding.catsmart.controllers;

import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.EnumFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import io.github.zhiweicoding.catsmart.model.Device;
import io.github.zhiweicoding.catsmart.model.Model;
import io.github.zhiweicoding.catsmart.model.OrderEntity;
import io.github.zhiweicoding.catsmart.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * 商品订单管理界面控制器
 *
 * @creator by zhiwei
 * @email diaozhiwei2k@163.com
 */
public class GoodOrderViewController implements Initializable {

    @FXML
    private MFXPaginatedTableView<OrderEntity> paginated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupPaginated();

        paginated.autosizeColumnsOnInitialization();

        When.onChanged(paginated.currentPageProperty())
                .then((oldValue, newValue) -> paginated.autosizeColumns())
                .listen();
    }

    private void setupPaginated() {
        MFXTableColumn<OrderEntity> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(OrderEntity::getID));
        MFXTableColumn<OrderEntity> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(OrderEntity::getName));
        MFXTableColumn<OrderEntity> ipColumn = new MFXTableColumn<>("IP", false, Comparator.comparing(OrderEntity::getIP));
        MFXTableColumn<OrderEntity> ownerColumn = new MFXTableColumn<>("Owner", false, Comparator.comparing(OrderEntity::getOwner));
        MFXTableColumn<OrderEntity> stateColumn = new MFXTableColumn<>("State", false, Comparator.comparing(OrderEntity::getState));

        idColumn.setRowCellFactory(device -> new MFXTableRowCell<>(OrderEntity::getID));
        nameColumn.setRowCellFactory(device -> new MFXTableRowCell<>(OrderEntity::getName));
        ipColumn.setRowCellFactory(device -> new MFXTableRowCell<>(OrderEntity::getIP) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        ownerColumn.setRowCellFactory(device -> new MFXTableRowCell<>(OrderEntity::getOwner));
        stateColumn.setRowCellFactory(device -> new MFXTableRowCell<>(OrderEntity::getState));
        ipColumn.setAlignment(Pos.CENTER_RIGHT);


        paginated.getTableColumns().addAll(idColumn, nameColumn, ipColumn, ownerColumn, stateColumn);
        paginated.getFilters().addAll(
                new IntegerFilter<>("ID", OrderEntity::getID),
                new StringFilter<>("Name", OrderEntity::getName),
                new StringFilter<>("IP", OrderEntity::getIP),
                new StringFilter<>("Owner", OrderEntity::getOwner),
                new EnumFilter<>("State", OrderEntity::getState, OrderEntity.State.class)
        );
        paginated.setItems(Model.orderEntities);
    }
}
