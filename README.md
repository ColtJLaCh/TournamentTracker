# Tournament Tracker

## Authors:
Noah Burrows and Colton LaChance

## Description

Tournament Tracker is a simple app used to keep track of tournaments, as the name would suggest. 
Users may log in to their account and keep track of many different tournament databases.
Tournament Tracker has support for singles tournaments, as well as team tournaments, and can
keep track of individual stats within these tournaments.

## Visuals
#### Home Screen
![Home](https://github.com/ColtJLaCh/TournamentTracker-JavaFinal-NC/blob/4669ff8c7a03af6511004b425431c825b7c42356/README-Home%20Screen.png)

#### View Tournaments
![View](https://github.com/ColtJLaCh/TournamentTracker-JavaFinal-NC/blob/4669ff8c7a03af6511004b425431c825b7c42356/README-View%20Tourney.png)

#### View Statistics
![Stats](https://github.com/ColtJLaCh/TournamentTracker-JavaFinal-NC/blob/6c7e3b12f42b72337dc2cb2174c724f570fef36c/README-View%20Stats.png)

#### Create a Tournament
![Create](https://github.com/ColtJLaCh/TournamentTracker-JavaFinal-NC/blob/4669ff8c7a03af6511004b425431c825b7c42356/README-Create.png)

#### Update a Tournament
![Update](https://github.com/ColtJLaCh/TournamentTracker-JavaFinal-NC/blob/4669ff8c7a03af6511004b425431c825b7c42356/README-Update.png)

#### Delete a Tournament
![Delete](https://github.com/ColtJLaCh/TournamentTracker-JavaFinal-NC/blob/4669ff8c7a03af6511004b425431c825b7c42356/README-Delete.png)

## Getting Started
To get started, you'll need to have an account. If you do not already have an account, you can go into the accounts.txt and databases.txt files to add one.

Once you have a valid account, make sure you are logged into the scweb server using Putty or a similar method. 
When you start the application, you should be taken to the home screen seen in visuals. Here you must enter your credentials before you can continue.

Once you are logged in, you will be presented with tabs representing all tournaments you are currently keeping track of. If you do not yet have a tournament, you can create one by clicking on File->Create New...

This will take you to the Tournament Creation page. From here you can specify the name of your tournament, the statistics you want to keep track of, and any players participating in the tournament. If your tournament is a team sport, you can specify this as well. Once you are satisfied with the tournament settings, click "CREATE TOURNAMENT" to finalize it.

You will be taken back to the view screen for your new tournament. From here you can update this tournament, switch to viewing stats, or delete the tournament.

To update the tournament, click on File->Update Tournament. Here you can specify which player you would like to update, which property you would like to update, and what the new value for that property will be. For team tournaments, you can also specify which team you are updating. Clicking "Update" will add the new value to that player. You can click View->View Tournament to return to the tournament, or View->View Statistics to view the statistics you've changed.

From the View Statistics screen, you are able to see the wins, losses, and any other specified categories in your tournament. Players are automatically filtered by highest stats to lowest.

Should you wish to delete your tournament, you can click on File->Delete Tournament. Here you will be given the option to delete your tournament from your database. You will be asked for confirmation first, but if you are sure then your tournament is lost forever.

When you are done using the app, you may click File->Exit to exit, or simply click the X in the top right corner.

### Licence
##### Tournament Tracker by Noah Burrows and Colton LaChance is free to use for anyone.
