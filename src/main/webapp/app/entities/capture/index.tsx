import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Capture from './capture';
import CaptureDetail from './capture-detail';
import CaptureUpdate from './capture-update';
import CaptureDeleteDialog from './capture-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CaptureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CaptureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CaptureDetail} />
      <ErrorBoundaryRoute path={match.url} component={Capture} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CaptureDeleteDialog} />
  </>
);

export default Routes;
