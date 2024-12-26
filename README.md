# Vehicle License Plate Reader

A desktop application built with Java, JavaFX, and Spring Boot for API integration. The application captures and reads vehicle license plates from video streams using prebuilt object detection and Optical Character Recognition (OCR) technologies.

## Features

- **License Plate Detection and Recognition**:
  - Captures license plates of moving vehicles from video streams.
  - Enhances the captured image for better accuracy.
  - Recovers and displays license plate numbers using OCR.
  - Supplementing OCR output with regular expressions to correctly identify license plates
- **Video Stream Management**:
  - View multiple video streams on a single page.
  - Select a video stream for license plate capturing.
- **Data Display**:
  - Displays captured license plate details.
  - Includes history, showing previously captured license plates and associated data.
- **Data Storage**:
  - Stores captured license plate information securely.

## Technologies Used

- **JavaFX**: For building a modern and responsive user interface.
- **Spring Boot**: For API integration and backend services.
- **Object Detection and OCR**: Prebuilt libraries for accurate license plate detection and character recognition.

## How to Use

1. Launch the application.
2. Navigate to the video streams page to view live feeds.
3. Select a video stream to focus on.
4. Capture the license plate by clicking the capture button.
5. View real-time details of the captured license plate on the details page.
6. Access the history page to review previously captured license plates and their details.

