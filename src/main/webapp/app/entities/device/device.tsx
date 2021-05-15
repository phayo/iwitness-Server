import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './device.reducer';
import { IDevice } from 'app/shared/model/device.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeviceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Device = (props: IDeviceProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { deviceList, match, loading } = props;
  return (
    <div>
      <h2 id="device-heading" data-cy="DeviceHeading">
        <Translate contentKey="iwitnessApp.device.home.title">Devices</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="iwitnessApp.device.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="iwitnessApp.device.home.createLabel">Create new Device</Translate>
          </Link>
        </div>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('iwitnessApp.device.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {deviceList && deviceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="iwitnessApp.device.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.blacklisted">Blacklisted</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.hash">Hash</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.linkDate">Link Date</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.blacklistDate">Blacklist Date</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.blacklistReason">Blacklist Reason</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.imei">Imei</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.cameraPermGranted">Camera Perm Granted</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.locationPermGranted">Location Perm Granted</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.bgInternetPermGranted">Bg Internet Perm Granted</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.os">Os</Translate>
                </th>
                <th>
                  <Translate contentKey="iwitnessApp.device.capture">Capture</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {deviceList.map((device, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${device.id}`} color="link" size="sm">
                      {device.id}
                    </Button>
                  </td>
                  <td>{device.type}</td>
                  <td>{device.blacklisted ? 'true' : 'false'}</td>
                  <td>{device.hash}</td>
                  <td>{device.name}</td>
                  <td>{device.linkDate ? <TextFormat type="date" value={device.linkDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{device.blacklistDate ? <TextFormat type="date" value={device.blacklistDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{device.blacklistReason}</td>
                  <td>{device.imei}</td>
                  <td>
                    {device.cameraPermGranted ? <TextFormat type="date" value={device.cameraPermGranted} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {device.locationPermGranted ? (
                      <TextFormat type="date" value={device.locationPermGranted} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {device.bgInternetPermGranted ? (
                      <TextFormat type="date" value={device.bgInternetPermGranted} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`iwitnessApp.DeviceStatus.${device.status}`} />
                  </td>
                  <td>
                    <Translate contentKey={`iwitnessApp.OS.${device.os}`} />
                  </td>
                  <td>{device.capture ? <Link to={`capture/${device.capture.id}`}>{device.capture.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${device.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${device.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${device.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="iwitnessApp.device.home.notFound">No Devices found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ device }: IRootState) => ({
  deviceList: device.entities,
  loading: device.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Device);
