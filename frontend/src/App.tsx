// frontend/src/App.tsx
import React, { useState } from 'react';

function App() {
  const [original, setOriginal] = useState<string | null>(null);
  const [processed, setProcessed] = useState<string | null>(null);
  const [file, setFile] = useState<File | null>(null);
  const [clipLimit, setClipLimit] = useState<number>(2.0);
  const [tileSize, setTileSize] = useState<number>(8);

  const handleUpload = async () => {
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('clipLimit', clipLimit.toString());
    formData.append('tileSize', tileSize.toString());

    const res = await fetch('/api/image/clahe', {
      method: 'POST',
      body: formData,
    });
    const data = await res.json();
    setOriginal(`data:image/png;base64,${data.originalImage}`);
    setProcessed(`data:image/png;base64,${data.claheImage}`);
  };

  return (
    <div>
      <h1>CLAHE Bildbearbeitung</h1>
      <input type="file" accept="image/*" onChange={(e) => setFile(e.target.files?.[0] || null)} />
      <div>
        <label>Clip Limit:</label>
        <input type="number" step="0.1" value={clipLimit} onChange={(e) => setClipLimit(parseFloat(e.target.value))} />
      </div>
      <div>
        <label>Tile Size:</label>
        <input type="number" value={tileSize} onChange={(e) => setTileSize(parseInt(e.target.value))} />
      </div>
      <button onClick={handleUpload}>Verarbeiten</button>
      <div style={{ display: 'flex', gap: '20px' }}>
        {original && <img src={original} alt="Original" width="300" />}
        {processed && <img src={processed} alt="CLAHE" width="300" />}
      </div>
    </div>
  );
}

export default App;
