# Path History

## Overview
Creates a back up for desired folders recursively, periodically. Whenever a file in a source path is updated, the newer version is copied to target path, and the previous version is timestamped.

## How to Run
- Build with `mvn install`.
- Execute the .jar file with `nohup java -jar path-history-0.1.jar &`.
- File `path-history.properties` must exist in the path, with contents similar to:

```properties
source-paths=/Users/kt/test
target-path=/Volumes/pd/path-history
```

- To quit, find the process id: `jps` and `kill #processId`

### Sample Run
Imagine I have the file `/Users/kt/test/1.txt` with contents `Version - 1`. The first time `path-history` runs, it will create `/test/1.txt` under `/Volumes/pd/path-history` with contents `Version - 1`.
The second time it runs, it will not do anything, since the contents of `/Users/kt/test/1.txt` has not changed. If I modify the file `1.txt`, imagine with contents `Version - 2`; the next time `path-history` runs,
2 files will exist under  `/Volumes/pd/path-history`:

`1.txt` with contents `Version - 2` and the previous version with name similar to `1-190901_122045` with contents `Version - 1`.

## How to Stop


## Planned Work
- Parametrize schedule, which is fixed to 5 minutes currently 
- Automated tests
- Logging
- Exception Handling
  - Target path does not exist
  - Source path does not exist
- Being able to fine tune what to backup
  - Ignoring specific-sub folders
  - Ignoring specific extensions
  - Ignoring specific file names
- Dockerize
- Clean up back up files older than a specific time
  - Or keep latest version of a file for a single day perhaps
