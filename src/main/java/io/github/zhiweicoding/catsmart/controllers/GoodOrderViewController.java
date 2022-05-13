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
import io.github.zhiweicoding.catsmart.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * 商品订单管理界面控制器
 * @creator by zhiwei
 * @email diaozhiwei2k@163.com
 */
public class GoodOrderViewController implements Initializable {

	@FXML
	private MFXPaginatedTableView<Device> paginated;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupPaginated();

		paginated.autosizeColumnsOnInitialization();

		When.onChanged(paginated.currentPageProperty())
				.then((oldValue, newValue) -> paginated.autosizeColumns())
				.listen();
	}

	private void setupPaginated() {
		MFXTableColumn<Device> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(Device::getID));
		MFXTableColumn<Device> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Device::getName));
		MFXTableColumn<Device> ipColumn = new MFXTableColumn<>("IP", false, Comparator.comparing(Device::getIP));
		MFXTableColumn<Device> ownerColumn = new MFXTableColumn<>("Owner", false, Comparator.comparing(Device::getOwner));
		MFXTableColumn<Device> stateColumn = new MFXTableColumn<>("State", false, Comparator.comparing(Device::getState));

		idColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getID));
		nameColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getName));
		ipColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getIP) {{
			setAlignment(Pos.CENTER_RIGHT);
		}});
		ownerColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getOwner));
		stateColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getState));
		ipColumn.setAlignment(Pos.CENTER_RIGHT);


		paginated.getTableColumns().addAll(idColumn, nameColumn, ipColumn, ownerColumn, stateColumn);
		paginated.getFilters().addAll(
				new IntegerFilter<>("ID", Device::getID),
				new StringFilter<>("Name", Device::getName),
				new StringFilter<>("IP", Device::getIP),
				new StringFilter<>("Owner", Device::getOwner),
				new EnumFilter<>("State", Device::getState, Device.State.class)
		);
		paginated.setItems(Model.devices);
	}
}
